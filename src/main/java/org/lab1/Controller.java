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
    public BuddyInfo createBuddyInfo(@PathVariable String phoneNo, @RequestParam("name") String name){
        return buddyInfoRepository.save(new BuddyInfo(name, phoneNo));
    }

    /**
     * Reassigns a BuddyInfo an ID. The id attribute has already been set when the BuddyInfo object was initialized.
     * @param id Identification number that the BuddyInfo can be referenced by
     * @param buddy The BuddyInfo to apply the reassignment to
     */
    @PutMapping("newBuddy/{id}")
    public void addBuddyInfo(@PathVariable Long id, @RequestBody BuddyInfo buddy){
        buddy.setId(id);
    }


    @DeleteMapping("delBud/{id}")
    public void removeBuddyInfo(@PathVariable Long id){
        buddyInfoRepository.deleteById(id);
    }

    @PutMapping("addressBooks/{addressBookId}/addBuddy/{buddyId}")
    public void addBuddyToBook(@PathVariable Long addressBookId, @PathVariable Long buddyId, BuddyInfo buddy){

        buddyInfoRepository.save(buddy);
    }

    @GetMapping("buddies")
    public Iterable<BuddyInfo> getBuddies(){
        return buddyInfoRepository.findAll();
    }

    //AddressBook Controller
}
