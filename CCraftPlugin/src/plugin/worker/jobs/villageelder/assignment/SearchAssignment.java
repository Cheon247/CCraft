/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.jobs.villageelder.assignment;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class SearchAssignment {

    private final String id = UUID.randomUUID().toString();
    private final int SUPER_FIELD_SIZE = 64; // TOTAL SIZE
    private final int MIN_SIZE = 16;         // MIN SIZE (size that will be assigned to the 
    private final Field field;

    /**
     * The location to start, this location will be used to determine the nearest chunk
     *
     * @param startlocation
     */
    public SearchAssignment(final Location startlocation) {
        Location location = new Location(
                startlocation.getWorld(),
                startlocation.getChunk().getX(),
                startlocation.getBlockY(), // The Y doesnt really matter
                startlocation.getChunk().getZ());
        this.field = new Field(location.add(-SUPER_FIELD_SIZE / 2, 0, -SUPER_FIELD_SIZE / 2), SUPER_FIELD_SIZE, 0, null); // id start with 0
        Debug.info("amount of search fields: " + field.size());
        Debug.info("levels: " + field.height());
        Debug.info("\n");
        field.print();
        Debug.info("\n");
        field.pop();
        field.pop();
        field.print();
        Debug.info("\n");
    }

    private class Field {

        private final int id;
        private final short NOT_ASSIGNED = 0;
        private final short ASSIGNED = 5;
        private final short COMPLETED = 10;

        private final Location origin;
        private final Field[] subfields;
        private final int field_size;
        private final Field parent;
        private int status = NOT_ASSIGNED;

        private Field(Location startLocation, int start_size, int id, Field parent) {
            this.origin = startLocation;
            this.field_size = start_size;
            this.id = id;
            this.parent = parent;

            if (start_size == SUPER_FIELD_SIZE) {
                Debug.info("SuperField:" + startLocation.getBlockX() + " : " + startLocation.getBlockZ());
            }

            if (start_size > MIN_SIZE) {
                subfields = new Field[4];

                subfields[0] = new Field(startLocation, start_size / 2, id++, this);
                subfields[1] = new Field(startLocation.clone().add(0, 0, start_size / 2), start_size / 2, id++, this);
                subfields[2] = new Field(startLocation.clone().add(start_size / 2, 0, start_size / 2), start_size / 2, id++, this);
                subfields[3] = new Field(startLocation.clone().add(start_size / 2, 0, 0), start_size / 2, id++, this);

            } else {
                subfields = null;
            }
        }

        void setAssigned() {
            status = ASSIGNED;
        }

        void setCompleted() {
            status = COMPLETED;
        }

        void notifyComplete() {
            if (parent.allSubfieldsComplete()) {
                parent.setCompleted();
                if (parent.getParent() != null) {
                    parent.getParent().notifyComplete();
                }
            }
        }

        void notifyAssign() {
            if (parent.allSubFieldsAssigned()) {
                parent.setAssigned();
                if (parent.getParent() != null) {
                    parent.getParent().notifyAssign();
                }
            }
        }

        void print() {
            if (field_size == MIN_SIZE) {
                Debug.info(this.toString());
            } else {
                for (Field f : subfields) {
                    f.print();
                }

            }
        }

        Field getParent() {
            return parent;
        }

        Field getLowest() {
            if (field_size == MIN_SIZE) {
                return this; // I was the target!
            } else {
                Field target = null;
                for (Field f : subfields) {
                    if (f.getStatus() != COMPLETED && target == null || f.getStatus() != COMPLETED && f.getProgress() > target.getProgress()) {
                        target = f;
                    }
                }
                if (target != null && target.getLowest() != null) {
                    return target.getLowest();
                }
                return target; // return null
            }
        }

        Field pop() {
            Field f = getLowest();
            if (f != null) {
                f.setAssigned();
                f.notifyAssign();

            }
            return f;
        }

        boolean setCompleted(Location location) {
            if (field_size == MIN_SIZE
                    && origin.equals(location)) {
                status = COMPLETED;
                return true;
            } else {
                for (Field f : subfields) {
                    if (f.setCompleted(location)) {
                        notifyComplete();
                        return true;
                    }
                }
                return false;
            }
        }

        private boolean allSubfieldsComplete() {
            boolean allComplete = true;
            for (Field f : subfields) {
                if (f.status != COMPLETED) {
                    allComplete = false;
                }
            }
            return allComplete;
        }

        private boolean allSubFieldsAssigned() {
            boolean allAssigned = true;
            for (Field f : subfields) {
                if (f.status != ASSIGNED) {
                    allAssigned = false;
                }
            }
            return allAssigned;
        }

        Location getFieldLocation() {
            return origin;
        }

        int getProgress() {
            int i = status;
            if (hasChildren()) {
                for (Field f : subfields) {

                    i += f.getProgress();
                }
            }
            return i;
        }

        int getStatus() {
            return status;
        }

        int getFieldSize() {
            return field_size;
        }

        /**
         * Returns the total amount of searchfields
         *
         * @return
         */
        int size() {
            if (field_size == MIN_SIZE) {
                return 1;
            } else {
                int i = 0;
                for (Field f : subfields) {
                    i += f.size();
                }
                return i;
            }
        }

        /**
         * Gets the amount of levels
         *
         * @return The dept of this Field
         */
        int height() {
            int i = 1;
            if (hasChildren()) {
                i += subfields[0].height();
            }
            return i;
        }

        boolean hasChildren() {
            return subfields != null;
        }

        @Override
        public String toString() {
            String s;
            if (status == COMPLETED) {
                s = "COMPLETED";
            } else if (status == ASSIGNED) {
                s = "ASSIGNED";
            } else {
                s = "UNASSIGNED";
            }
            return "[ID: " + id + " Location: " + origin + " Status: " + s;
        }

    }

}
