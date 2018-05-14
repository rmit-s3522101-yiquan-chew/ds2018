/*
* This is a modified version of dbquery.java
* for loading heap.pagesize and export it to index.pagesize
*/

import java.io.*;
import java.util.*;

public class hashload {
    public static void main(String[] args) {
        int pagesize = 0;
        try{
            pagesize = Integer.parseInt(args[0]);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Invalid arguments. Please try again");
            System.exit(0);
        }
        long startTime, endTime, duration;
        
        startTime = System.currentTimeMillis();
        
        //System.out.println(hash("hellow world").toString());
        hashLoad(pagesize);
        
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Time taken for index." + pagesize + " : " + (duration/1000) + "s");
    }

    public static void hashLoad(int pagesize){
        //a hashmap for hashtable. temporarily storing all index and bucket
        Map<Integer, List<String>> hashMap = new HashMap<Integer, List<String>>();
        
        File file = new File("heap" + "." + pagesize);
        
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[pagesize];
            
            String in;
            int pageCount = 0;
            
            while(is.read(buffer) != -1){
                in = new String(buffer);
                
                System.out.print("Indexing page " + pageCount + "\r");
                
                String[] page = in.split("\r\n");
                
                for(int j=0; j<page.length-1; j++){
                    String[] token = page[j].split("\t");
                    
                    for(int i=0; i<token.length; i++){
                        //split the datainto 2 part
                        String[] data = token[i].split(":",2);
                        
                        //at this point, only work on BN_NAME
                        if(data[0].equals("BN_NAME")){
                            //hashing in BN_NAME
                            String location = "page" + pageCount + "line" + (j+1);
                            
                            //hash and stored into index
                            hashMap = hash(data[1], location, hashMap);
                        }
                    }
                }
                pageCount++;
            }
            System.out.println("Writing to index files...");
            //write map to file
            mapToFile(hashMap, pagesize);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /*
    * For hashing string, should return int   
    */
    public static double tableSize = Math.pow(2, 10)-1;
    public static double bucketSize = Math.pow(2, 12)-1;
    public static Map<Integer, List<String>> hash(String search, String location, Map<Integer, List<String>> hashTable){
        int[] index = new int[2];
        int temp = Math.abs(search.hashCode());
        
        index[1] = (int)Math.abs((search.hashCode() % bucketSize));
        //System.out.println("1st hash:" + index[1]);
        
        index[0] = (int)(index[1] % tableSize);
        //System.out.println(" : 2nd hash: " + index[0]);
        
        String hashData = temp + "," + location;
        //insert data into hashTable
        List<String> tempList = new ArrayList<String>();
        
        if(hashTable.containsKey(index[0])){
            tempList = hashTable.get(index[0]);
        }
        
        tempList.add(hashData);
        hashTable.put(index[0], tempList);
        
        //System.out.println(hashTable.get(index[0]));
        
        return hashTable;
    }
    
    //For writing hashTable to file
    public static void mapToFile(Map<Integer, List<String>> hashTable, int pagesize){
        //a hashmap for hashtable. temporarily storing all index and bucket
        List<String> sortedList = new ArrayList<String>();
        
        File file = new File("index" + "." + pagesize);
        
        try {
            PrintWriter pw = new PrintWriter(file);
            
            for(int i=0; i<tableSize; i++){
                pw.println("p" + i);
                sortedList = hashTable.get(i);
                Collections.sort(sortedList);
                
                if(sortedList != null && sortedList.size() > 0){
                    for(int j=0; j<sortedList.size(); j++){
                    pw.println(sortedList.get(j));
                    }
                }
            }
            
            //pw.close();
            pw.close();
            
            System.out.println("Finish loading.");
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
