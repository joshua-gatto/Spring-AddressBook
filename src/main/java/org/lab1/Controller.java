package org.lab1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * The Controller class is a RESTful web service class used to handle incoming HTTP requests. It handles the functionality
 * of creating and modifying BuddyInfo and AddressBook objects.
 * @Author Joshua Gatto
 */
@RestController
public class Controller {

    private static BuddyInfoRepository buddyInfoRepository;
    private static AddressBookRepository addressBookRepository;

    /**
     * Initializes the controller with necessary BuddyInfo and AddressBook repositories.
     * @param buddyInfoRepository BuddyInfo repository
     * @param addressBookRepository AddressBook repository
     */
    @Autowired
    public Controller(BuddyInfoRepository buddyInfoRepository, AddressBookRepository addressBookRepository){
        Controller.buddyInfoRepository = buddyInfoRepository;
        Controller.addressBookRepository = addressBookRepository;
    }

    //BuddyInfo Controller
        //Constructor

    /**
     * Main constructor for BuddyInfo. The id attribute is automatically generated and assigned.
     * @param phoneNo Buddy's phone number
     * @param name Buddy's name
     * @return new BuddyInfo object
     */
    @PostMapping("createBuddyInfo/name={name}-phoneNo={phoneNo}")
    public BuddyInfo createBuddyInfo(@PathVariable String phoneNo, @PathVariable("name") String name){
        return buddyInfoRepository.save(new BuddyInfo(name, phoneNo));
    }

        //Getters

    /**
     * Retrieves the list of all BuddyInfos in the repository.
     * @return Iterable BuddyInfo collection
     */
    @GetMapping("buddies")
    public Iterable<BuddyInfo> getBuddies(){
        return buddyInfoRepository.findAll();
    }

    /**
     * Retrieve a BuddyInfo by identification number from the repository
     * @param id identification number of the desired BuddyInfo
     * @return BuddyInfo of specified id number, null if no BuddyInfo is assigned the specified id
     */
    @GetMapping("getBuddy/id/{id}")
    public BuddyInfo getBuddyInfo(@PathVariable Long id){
        return buddyInfoRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves all BuddyInfos with the specified name.
     * @param name name of the desired BuddyInfo
     * @return an array of BuddyInfos with the specified name
     */
    @GetMapping("getBuddy/name/{name}")
    public BuddyInfo[] getBuddyInfo(@PathVariable String name){
        return buddyInfoRepository.findAllByName(name);
    }

        //Setters

    /**
     * Reassigns a BuddyInfo an id. Any AddressBooks that contain the specified BuddyInfo will have it removed before
     * it is updated and added again. The id attribute has already been set when the BuddyInfo object was initialized.
     * Returns null if no BuddyInfo of specified oldId exists. NOTE: You should not set the id, especially to a value
     * that is already in use.
     * @param oldId The BuddyInfo to change the id of
     * @param newId The new identification number of the BuddyInfo
     */
    @PutMapping("setId/{oldId}/{newId}")
    public BuddyInfo setBuddyInfoId(@PathVariable Long oldId, @PathVariable Long newId){
        BuddyInfo buddy = getBuddyInfo(oldId);
        if(buddy != null){ //checks if buddy retrieval was successful
            AddressBook[] fromBook = new AddressBook[(int)addressBookRepository.count()]; //empty array to store AddressBooks that contain specified BuddyInfo
            int i = 0;
            for(AddressBook addressBook : addressBookRepository.findAll()){ //iterating over all AddressBooks in repository
                if(addressBook.containsBuddy(buddy)){ //checking if the BuddyInfo is in the AddressBook
                    addressBook.removeBuddy(buddy); //remove the BuddyInfo
                    fromBook[i] = addressBook; //Add AddressBook to array
                    addressBookRepository.save(addressBook); //update the AddressBook's repository
                    i++;
                }
            }
            buddyInfoRepository.delete(buddy); //deleting old BuddyInfo
            buddy.setId(newId); //update id attribute
            buddyInfoRepository.save(buddy); //add BuddyInfo to repository
            for(AddressBook addressBook : fromBook){
                addressBook.addBuddy(buddy);
                addressBookRepository.save(addressBook);
            }
            return buddy;
        }else{
            return null;
        }
    }

    @PutMapping("setName/{id}/{name}")
    public BuddyInfo setName(@PathVariable Long id, @PathVariable String name){
        BuddyInfo buddy = buddyInfoRepository.findById(id).get();
        buddy.setName(name);
        return buddyInfoRepository.save(buddy);
    }

    @PutMapping("setPhoneNo/{id}/{phoneNo}")
    public BuddyInfo setPhoneNo(@PathVariable Long id, @PathVariable String phoneNo){
        BuddyInfo buddy = buddyInfoRepository.findById(id).get();
        buddy.setPhoneNo(phoneNo);
        return buddyInfoRepository.save(buddy);
    }

    //AddressBook Controller
        //Constructor
    @PostMapping("createAddressBook")
    public Long createAddressBook(){
        AddressBook newBook = new AddressBook();
        addressBookRepository.save(newBook);
        return newBook.getId();
    }

        //Getter

    @GetMapping("getAddressBook/id/{id}")
    public AddressBook getAddressBookById(@PathVariable Long id){
        return addressBookRepository.findById(id).orElse(null);
    }

    @GetMapping("getAddressBooks/BuddyInfo/Id/{id}")
    public AddressBook[] getAddressBookByBuddyInfoId(@PathVariable Long id){
        BuddyInfo buddy = getBuddyInfo(id);
        ArrayList<AddressBook> booksWithBuddy = new ArrayList<>();
        Iterable<AddressBook> addressbooksInRepo = addressBookRepository.findAll();
        for(AddressBook selectedBook : addressbooksInRepo){
            if(selectedBook.containsBuddy(buddy)){
                booksWithBuddy.add(selectedBook);
            }
        }
        return (AddressBook[]) booksWithBuddy.toArray();
    }

    @GetMapping("getAddressBooks/BuddyInfo/Name/{name}")
    public AddressBook[] getAddressBooksByBuddyInfoName(@PathVariable String name){
        Iterable<BuddyInfo> buddies = getBuddies();
        ArrayList<BuddyInfo> buddiesWithName = new ArrayList<>();
        for(BuddyInfo b : buddies){
            if(b.getName() == name){
                buddiesWithName.add(b);
            }
        }
        ArrayList<AddressBook> addressBooksWithBuddyName = new ArrayList<>();
        Iterable<AddressBook> addressBooksInRepo = addressBookRepository.findAll();
        for(AddressBook addressBook : addressBooksInRepo){
            for(BuddyInfo b : buddiesWithName){
                if(addressBook.containsBuddy(b)){
                    addressBooksWithBuddyName.add(addressBook);
                }
            }
        }
        return (AddressBook[]) addressBooksWithBuddyName.toArray();
    }

    @GetMapping("getAddresBooks/BuddyInfo/PhoneNo/{phoneNo}")
    public AddressBook[] getAddressBooksByBuddyInfoPhoneNo(@PathVariable String phoneNo){
        Iterable<BuddyInfo> buddies = getBuddies();
        ArrayList<BuddyInfo> buddiesWithPhoneNo = new ArrayList<>();
        for(BuddyInfo b : buddies){
            if(b.getPhoneNo() == phoneNo){
                buddiesWithPhoneNo.add(b);
            }
        }
        ArrayList<AddressBook> addressBooksWithBuddyPhoneNo = new ArrayList<>();
        Iterable<AddressBook> addressBooksInRepo = addressBookRepository.findAll();
        for(AddressBook addressBook : addressBooksInRepo){
            for(BuddyInfo b : buddiesWithPhoneNo){
                if(addressBook.containsBuddy(b)){
                    addressBooksWithBuddyPhoneNo.add(addressBook);
                }
            }
        }
        return (AddressBook[]) addressBooksWithBuddyPhoneNo.toArray();
    }

        //Buddy Maintenance Methods

    /**
     * Adds a BuddyInfo to an AddressBook.
     * @param addressBookId Identification number of the AddressBook
     * @param buddyId Identification number of the BuddyInfo
     * @param buddy BuddyInfo to Add to AddressBook
     */
    @PutMapping("addressBooks/{addressBookId}/addBuddy/{buddyId}")
    public void addBuddyToBook(@PathVariable Long addressBookId, @PathVariable Long buddyId, BuddyInfo buddy){
        buddyInfoRepository.save(buddy);
    }

    /**
     * Removes a BuddyInfo from an AddressBook.
     * @param id Identification number of the BuddyInfo
     */
    @DeleteMapping("delBud/{id}")
    public void removeBuddyInfo(@PathVariable Long id){

    }
}
