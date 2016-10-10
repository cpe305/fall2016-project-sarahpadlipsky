/**
 * Represents a super user of the IOU app.
 * @author cesiu
 * @version October 10, 2016
 */

import java.util.ArrayList;

public class SuperUser {
   // The super user's groups
   private ArrayList<Group> groups;

   public SuperUser() {
      groups = new ArrayList<Group>();
   }

   /**
    * @return The super user's groups
    */
   public ArrayList<Group> getGroups() {
      return groups;
   }

   /**
    * @param The group to add to the super user's groups
    */
   public void addGroup(Group group) {
      groups.add(group);
   }
}
