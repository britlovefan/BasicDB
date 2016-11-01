package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 * 
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {
    private File file;
    private TupleDesc td;
    private ArrayList<Page> pages;
    /**
     * Constructs a heap file backed by the specified file.
     * 
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
    public HeapFile(File f, TupleDesc td) {
    	this.file = f;
    	this.td = td;
    	this.pages = new ArrayList<Page>();
        // some code goes here
    }

    /**
     * Returns the File backing this HeapFile on disk.
     * 
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here
        return file;
    }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     * 
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
        // some code goes here
    	return file.getAbsoluteFile().hashCode();
        //throw new UnsupportedOperationException("implement this");
    }

    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * 
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
    	return td;
        //throw new UnsupportedOperationException("implement this");
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
    	/*
    	HeapPageId hid = (HeapPageId)pid;
        BufferedInputStream bin = null;
        try{
            RandomAccessFile raf = new RandomAccessFile(file,"r");
        	bin = new BufferedInputStream(new FileInputStream(raf.getFD()));
        	byte[] pf = new byte[BufferPool.getPageSize()];
        	int check = bin.read(pf, 0, BufferPool.getPageSize());
        	if (check == -1) {
               throw new IllegalArgumentException("Read past end of table");
            }
        	if(check < BufferPool.getPageSize()){
        		throw new IllegalArgumentException("Read is failed");
        	}
        	HeapPage p = new HeapPage(hid, pf);
            return p;
        }
        catch(IOException e){
        	throw new RuntimeException();
        }
        finally{
        		try {
        			if(bin!=null)bin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }  */
    	int psize = BufferPool.getPageSize();
    	byte [] data = new byte[psize];
    	//byte [] data = new byte[(int)Math.min(psize, file.length() - psize*pages.size())];
    	try {
				RandomAccessFile rf = new RandomAccessFile(file, "r");
				rf.skipBytes(psize*pages.size());
				rf.read(data);
				rf.close();
				pages.add(new HeapPage((HeapPageId) pid, data));
				return pages.get(pages.size() - 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for lab1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        //return (int) (file.length()/BufferPool.getPageSize());
    	return (int) Math.ceil((this.file.length()/BufferPool.getPageSize()));
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> insertTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> deleteTuple(TransactionId tid, Tuple t) throws DbException,
            TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

   
    //http://users.eecs.northwestern.edu/~jennie/eecs339/labs/simpledb/doc/
    // iterate through through the tuples of each page in the HeapFile
    public DbFileIterator iterator(TransactionId tid) {
    	DbFileIterator iter = new HeapFileIterator(this,tid);
    	return iter;
    }
}
// define a helper class 
class HeapFileIterator implements DbFileIterator{
	
	HeapFile file;
	TransactionId tranId;
	int index;
	Iterator<Tuple> tupIter = null;
	boolean isOpen;
    public HeapFileIterator(HeapFile f,TransactionId id){
    	this.file = f;
    	this.tranId = id;
    	index = 0;
    }
	@Override
	public void open() throws DbException, TransactionAbortedException {
		//Do not load the entire table into memory on the open() call
		isOpen = true;
		HeapPageId hpi = new HeapPageId(file.getId(), index);
		BufferPool bp = Database.getBufferPool();
		HeapPage hp = (HeapPage)bp.getPage(tranId, hpi, Permissions.READ_ONLY);
		tupIter = hp.iterator();
	}

	@Override
	public boolean hasNext() throws DbException, TransactionAbortedException {
		if (isOpen) {
			if (tupIter == null) 
				return false;
			if (tupIter.hasNext())
				return true;
			while(index < file.numPages()-1) {
				index++;
				HeapPageId pid = new HeapPageId(file.getId(), index);
				HeapPage page = 
						(HeapPage) Database.getBufferPool().
						getPage(tranId, pid, Permissions.READ_ONLY);
				tupIter = page.iterator();
				if (tupIter.hasNext()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public Tuple next() throws DbException, TransactionAbortedException, NoSuchElementException {
		if (isOpen) {
			if (hasNext()) {
				return tupIter.next();
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public void rewind() throws DbException, TransactionAbortedException {
		// Resets the iterator to the start.
		open();
	}

	@Override
	public void close() {
		tupIter = null;
		isOpen = false;
		index = 0;	
	}
}

