import java.io.*;
import java.util.*;

public class dbload {

    public static void main(String[] args) {
        int pagesize = 0;
        String datafile = null;
        try{
            pagesize = Integer.parseInt(args[1]);
            datafile = args[2];    
        }
        catch(Exception e){
            System.out.println("Invalid arguments. Please try again");
            System.exit(0);
        }
        long startTime, endTime, duration;
        
        startTime = System.currentTimeMillis();
        heapFile(pagesize, datafile);
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Time taken for creating heapfile page size " + pagesize + ": " + (duration/1000) + "s");
    }

    public static void heapFile(int pagesize, String datafile){
        File file = new File("heap" + "." + pagesize);
        int pageNum = 1;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(datafile));
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            
            String in;
            int page = 0;
            byte[] buffer = new byte[0];
            
            String[] header = (br.readLine()).split("\t"); //read pass the header
            
            while((in = br.readLine()) != null){
                String data = new String();
                
                //split the line, skip register name
                String[] token = in.split("\t");
                
                for(int i=1; i<header.length; i++){
                    try{
                        data = data.concat(header[i] + ":" + token[i]);
                    }
                    catch(IndexOutOfBoundsException ie){
                        data = data.concat(header[i] + ":" + "N/A");
                    }
                    
                    if(i == header.length-1){
                        data = data.concat("\r\n");
                    }
                    else{
                        data = data.concat("\t");
                    }
                }
                byte[] b = data.getBytes();
                
                //make sure page is enough to contain the data
                if((page + b.length) < pagesize){
                    buffer = appendByte(b, buffer);
                    page += b.length;
                }
                else{
                    //ensure page page is filled to page size
                    byte[] temp = new byte[pagesize];
                    System.arraycopy(buffer, 0, temp, 0, buffer.length);
                    
                    os.write(temp);
                    //pw.println(Arrays.toString(buffer));
                    System.out.print("Writing page " + pageNum + ". Previous page size is " + temp.length + "\r");
                    
                    //flush buffer
                    buffer = new byte[0];
                    appendByte(b, buffer);
                    
                    page = b.length;
                    pageNum++;
                }
                
                /*/for testing
                if(pageNum == 10){
                    break;
                }*/
            }
            br.close();
            //pw.close();
            
            System.out.println();
            System.out.println("Finish loading.");
            System.out.println("Total page : " + pageNum);
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /* append data byte to destination byte
    */
    public static byte[] appendByte(byte[] data, byte[] destination){
        byte[] temp = new byte[destination.length + data.length];
        
        //copy all data into temp
        System.arraycopy(destination, 0, temp, 0, destination.length);
        System.arraycopy(data, 0, temp, destination.length, data.length);
        
        destination = temp;
        
        return destination;
    }
}
