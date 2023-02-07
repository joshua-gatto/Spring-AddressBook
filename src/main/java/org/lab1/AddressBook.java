package org.lab1;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies;

    public AddressBook(){
        buddies = new ArrayList<>();
    }

    public AddressBook(List<BuddyInfo> buds){
        buddies = buds;
    }

    public AddressBook(BuddyInfo[] buds){
        buddies = new ArrayList<>(List.of(buds));
    }

    public BuddyInfo getBuddy(int index){
        if(index < buddies.size()) {
            return buddies.get(index);
        }else{
            return null;
        }
    }

    public List<BuddyInfo> getBuddies(){
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> buds){
        this.buddies = buds;
    }

    public boolean containsBuddy(BuddyInfo bud){
        return buddies.contains(bud);
    }

    public boolean addBuddy(BuddyInfo bud){
        if(!buddies.contains(bud)){
            buddies.add(bud);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeBuddy(BuddyInfo bud){
        if(buddies.contains(bud)){
            buddies.remove(bud);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeBuddy(int index){
        if(index < buddies.size()){
            buddies.remove(index);
            return true;
        }else{
            return false;
        }
    }

    public void removeAll(){
        for(BuddyInfo b : buddies){
            buddies.remove(b);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        String bookString = "[    Address Book " + id + "    ]";
        for(BuddyInfo bud : buddies){
            bookString += "\n|----------------------|\n" + bud.toString();
        }
        return bookString + "\n|-----End of Book------|\n";
    }

    public static void main(String[] args) {
        BuddyInfo Alice = new BuddyInfo("Alice", "613-499-3231");
        BuddyInfo Bob = new BuddyInfo("Bob", "613-842-1395");
        BuddyInfo Charlie = new BuddyInfo("Charlie", "613-291-9950");
        BuddyInfo[] buds = {Alice, Bob, Charlie};
        AddressBook buddies = new AddressBook(buds);
        System.out.println(buddies);
    }
}
