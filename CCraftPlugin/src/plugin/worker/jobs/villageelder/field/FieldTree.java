/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.jobs.villageelder.field;

/**
 *
 * @author Chingo
 */
public class FieldTree {

    private static final int STATUS_COMPLETE = 10;
    private static final int STATUS_PROCESSING = 5;
    private static final int STATUS_UNTOUCHED = 0;
    private static final int CHUNK_SIZE = 16;
//    private final String worldid;
    private FieldTree parent;
    private FieldTree northEast;
    private FieldTree southEast;
    private FieldTree northWest;
    private FieldTree southWest;
    private final int MIN_SIZE; //
    private final int DIMENSION;
    private final int x;
    private final int z;
    private STATUS status = STATUS.UNTOUCHED;

    /**
     * Constructor.
     *
     * @param x the x coordinate, preferable a power of two, use the
     * @param z the z coordinate, must be a power of two
     * @param minSize the min size at which deviding has no more purpose, must 2x as big as
     * CHUNK_SIZE
     * @param dimension the start dimension of this tree
     */
    public FieldTree(int x, int z, int minSize, int dimension) {
        if (minSize < 0 || dimension < 0) {
            throw new IllegalArgumentException("dimension and minsize must be bigger that 0");
        } else if (dimension < minSize) {
            throw new IllegalArgumentException("start size must be bigger than min size");
        } else if (!checkPowerOfTwo(minSize) || !checkPowerOfTwo(dimension)) {
            throw new IllegalArgumentException("min size and dimension must be a power of two");
        } else if (minSize < CHUNK_SIZE) {
            throw new IllegalArgumentException("min size must be bigger or equal to CHUNK SIZE");
        }
        this.MIN_SIZE = minSize;
        this.DIMENSION = dimension;
        this.x = x;
        this.z = z;

        if (dimension != minSize) {
            this.southWest = new FieldTree(x, z, minSize, dimension / 2, this);
            this.northWest = new FieldTree(x, z + dimension / 2, minSize, dimension / 2, this);
            this.northEast = new FieldTree(x + dimension / 2, z + dimension / 2, minSize, dimension / 2, this);
            this.southEast = new FieldTree(x + dimension / 2, z, minSize, dimension / 2, this);
        }
    }

    private FieldTree(int x, int z, int minSize, int dimension, FieldTree parent) {
        assert isEven(x) && isEven(z) : "position has to be even but was : (" + x + "," + z + " )";
        this.MIN_SIZE = minSize;
        this.DIMENSION = dimension;
        this.x = x;
        this.z = z;
        this.parent = parent;

        if (dimension != minSize) {
            this.southWest = new FieldTree(x, z, minSize, dimension / 2, this);
            this.northWest = new FieldTree(x, z + dimension / 2, minSize, dimension / 2, this);
            this.northEast = new FieldTree(x + dimension / 2, z + dimension / 2, minSize, dimension / 2, this);
            this.southEast = new FieldTree(x + dimension / 2, z, minSize, dimension / 2, this);
        }
    }

    protected void assign() {
        if (this.status != STATUS.PROCESSING) {
            this.status = STATUS.PROCESSING;
            if (parent != null) {
                parent.assign();
            }
        }
    }

    protected void complete() {
        if (hasChildren()) {
            boolean complete = true;
            for (FieldTree ft : getFields()) {
                if (ft.getStatus() != STATUS.COMPLETE) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                this.status = STATUS.COMPLETE;
                this.northEast = null;
                this.northWest = null;
                this.southWest = null;
                this.southEast = null;
                parent.complete();
            }
        } else {
            this.status = STATUS.COMPLETE;
            parent.complete();
        }
    }

    public STATUS getStatus() {
        return status;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getDimension() {
        return DIMENSION;
    }

    public boolean hasDimension(int x, int z, int dimension) {
        if (dimension < MIN_SIZE) {
            throw new IllegalArgumentException("dimension smaller than " + MIN_SIZE);
        }

        if (this.DIMENSION == dimension) {
            return this.x == x && this.z == z;
        } else {
            for (FieldTree f : getFields()) {
                if (f.hasDimension(x, z, dimension)) {
                    return true;
                }
            }
            return false;
        }
    }

    public FieldTree poll(int size) {
        FieldTree tree = pop(size);
        if (tree != null) {
            tree.assign();
        }

        return tree;
    }

    /**
     * Determines wheter a tree has children
     *
     * @return
     */
    public boolean hasChildren() {
        return southEast != null;
    }

    /**
     * Call this when poll return null, but the tree isnt big enough
     * @return 
     */
    public FieldTree grow() {
        System.out.println("tree was has to grow");
        FieldTree nw = northWest;
        FieldTree ne = northEast;
        FieldTree se = southEast;
        FieldTree sw = southWest;
        
        FieldTree tree = new FieldTree(this.x - DIMENSION, this.z - DIMENSION, MIN_SIZE, DIMENSION * 2);
        tree.setDimension(nw);
        tree.setDimension(ne);
        tree.setDimension(se);
        tree.setDimension(sw);
        
        return tree;
    }
    
    FieldTree getNorthEast() {
        return northEast;
    }
    
    FieldTree getSouthEast() {
        return southEast;
    }
    FieldTree getNorthWest() {
        return northWest;
    }
    FieldTree getSouthWest() {
        return southWest;
    }

    private boolean setDimension(FieldTree field) {
        if (field.getDimension() < MIN_SIZE) {
            throw new IllegalArgumentException("field tree dimension smaller than " + MIN_SIZE);
        }

        if (this.DIMENSION == field.DIMENSION && this.x == field.getX() && this.z == field.getZ()) {
            this.northEast = field.northEast;
            this.northWest = field.northWest;
            this.southEast = field.southEast;
            this.southWest = field.southWest;
            return true;
        } else {
            for (FieldTree f : getFields()) {
                if (f.hasDimension(field.x, field.z, field.getDimension())) {
                    return true;
                }
            }
            return false;
        }
    }

    private FieldTree[] getFields() {
        FieldTree[] fields = {northEast, northWest, southEast, southWest};
        return fields;
    }

    private FieldTree pop(int size) {
        if (DIMENSION == size && this.status.getValue() < STATUS_PROCESSING) {
            return this;
        } else if (getLowest() != null) {
            return getLowest().pop(size);
        } else {
            return null;
        }
    }

    private boolean hasFreeDimension(int dimension) {
        if (this.status == STATUS.COMPLETE) {
            return false;
        }
        if (this.DIMENSION == dimension) {
            return this.getStatus() == STATUS.UNTOUCHED;
        } else {
            for (FieldTree f : getFields()) {
                if (f.hasFreeDimension(dimension)) {
                    return true;
                }
            }
            return false;
        }
    }

    private FieldTree getLowest() {
        FieldTree target = null;
        if (hasFreeDimension(MIN_SIZE)) {
            for (FieldTree f : getFields()) {
                if (target == null && f.hasFreeDimension(MIN_SIZE)
                        || target != null && f.hasFreeDimension(MIN_SIZE) && f.getStatus().value < target.getStatus().value) {
                    target = f;
                }
            }
        }
        return target;
    }

    @Override
    public String toString() {
        String s = DIMENSION + ": X:" + x + " Z:" + z + "status: " + status + "\n";
        if (hasChildren()) {
            s += "\t" + southWest.toString();
            s += "\t" + northWest.toString();
            s += "\t" + northEast.toString();
            s += "\t" + southEast.toString();
        } else {
            s = "\t" + s;
        }
        return s;
    }

    public enum STATUS {

        COMPLETE(STATUS_COMPLETE),
        PROCESSING(STATUS_PROCESSING),
        UNTOUCHED(STATUS_UNTOUCHED);
        private final int value;

        STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private boolean checkPowerOfTwo(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("number: " + number);
        }
        return ((number & (number - 1)) == 0);
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static void main(String args[]) {
        FieldTree tree = new FieldTree(0, 0, 16, 64);

        for (int i = 0; i < 17; i++) {
            if(tree.poll(tree.MIN_SIZE) == null) tree = tree.grow();
        }

        System.out.println(tree.toString());

        //for (int i = 0; i < 16; i++) {
        //    tree.poll(CHUNK_SIZE);
        //}
        //System.out.println(tree.toString());
    }
}
