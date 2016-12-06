package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
	private Type[] types;
	private String[] fieldName;
    public static class TDItem implements Serializable{
        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;

        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }
        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here
    	 return new Iterator<TDItem>(){
    		private int currentIndex = 0;
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return currentIndex < types.length;
			}
			@Override
			public TDItem next() {
				// TODO Auto-generated method stub
				TDItem item = new TDItem(types[currentIndex++],fieldName[currentIndex++]);
				return item;
			}
    	 };
    }
    

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
    	this.types = typeAr;
    	this.fieldName = fieldAr;
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
    	this.types = typeAr;
        // some code goes here
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return types.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
    	if(i<0||i>=fieldName.length){
    		throw new NoSuchElementException();
    	}
        return fieldName[i];
    }
    
    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
    	if(i<0||i>=types.length){
    		throw new NoSuchElementException();
    	}
        return types[i];
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
    	int res = -1;
    	if(name==null||fieldName==null)throw new NoSuchElementException();
    	for(int i = 0;i < fieldName.length;i++){
    		String cur = fieldName[i];
    		if(cur.equals(name)){
    			res = i;
    		}
    	}
    	if(res==-1){
    		throw new NoSuchElementException();
    	}
    	return res;
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
    	int res = 0;
    	for(Type t: types){
    		res+= t.getLen();
    	}
        // some code goes here
        return res;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
    	String[] name1 = td1.fieldName;
    	String[] name2 = td2.fieldName;
    	Type[] type1 = td1.types;
    	Type[] type2 = td2.types;
    	String[] mName = new String[name1.length+name2.length];
    	Type[] mType = new Type[type1.length+type2.length];
    	for(int i = 0;i < name1.length;i++){
    		mName[i] = name1[i];
    		mType[i] = type1[i];
    	}
    	for(int i = name1.length;i < mName.length;i++){
    		mName[i] = name2[i-name1.length];
    		mType[i] = type2[i-name1.length];
    	}
        // some code goes here
        return new TupleDesc(mType,mName);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        // some code goes here
    	if(o==null){
    		return false;
    	}
    	if(this==o){
    		return true;
    	}
    	if(getClass()!=o.getClass()){
    		return false;
    	}
    	TupleDesc comp = (TupleDesc)o;
    	Type[]type1 = comp.types;
    	if(comp.getSize()!=getSize())return false;
    	for(int i = 0;i < types.length;i++){
    		if(types[i]!=type1[i]){
    			return false;
    		}
    	}
    	return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0;i < types.length-1;i++){
    		String cur = types[i]+"("+fieldName[i]+")"+",";
    		sb.append(cur);
    	}
    	String last = types[types.length-1]+"("+fieldName[types.length-1]+")";
    	sb.append(last);
        // some code goes here
        return sb.toString();
    }
}
