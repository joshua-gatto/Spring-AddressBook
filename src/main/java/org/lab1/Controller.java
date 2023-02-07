package org.lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        this.buddyInfoRepository = buddyInfoRepository;
        this.addressBookRepository = addressBookRepository;
    }

    //BuddyInfo Controller

    /**
     * Creates a new BuddyInfo. The id attribute is automatically generated and assigned.
     * @param phoneNo Buddy's phone number
     * @param name Buddy's name
     * @return new BuddyInfo object
     */
    @PostMapping("createBud/name={name}-phoneNo={phoneNo}")
    public BuddyInfo createBuddyInfo(@PathVariable String phoneNo, @PathVariable("name") String name){
        return buddyInfoRepository.save(new BuddyInfo(name, phoneNo));
    }

    /**
     * Reassigns a BuddyInfo an ID. The id attribute has already been set when the BuddyInfo object was initialized.
     * @param id Identification number that the BuddyInfo can be referenced by
     * @param buddy The BuddyInfo to apply the reassignment to
     */
    @PutMapping("setId/{buddy}/{id}")
    public BuddyInfo addBuddyInfo(@PathVariable Long id, @RequestBody BuddyInfo buddy){
        buddy.setId(id);
        return buddyInfoRepository.save(buddy);
    }

    //AddressBook Controller
    /**
     * Retrieves the list of BuddyInfos in the AddressBook.
     * @return Iterable BuddyInfo collection
     */
    @GetMapping("buddies")
    public Iterable<BuddyInfo> getBuddies(){
        return buddyInfoRepository.findAll();
    }

    /**
     * Adds a BuddyInfo to an AddressBook.
     * @param addressBookId Identification number of the AddressBook
     * @param buddyId Identification number of the BuddyInfo
     * @param buddy
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
        buddyInfoRepository.deleteById(id);
    }
}
