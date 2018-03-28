import java.io.*;

public class dbquery {

    public static void main(String[] args) {
        String query = null;
        int pagesize = 0;
        try{
            query = args[0];
            pagesize = Integer.parseInt(args[1]);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Invalid arguments. Please try again");
            System.exit(0);
        }
        long startTime, endTime, duration;
        
        startTime = System.currentTimeMillis();
        searchText(query, pagesize);
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Time taken for search : " + (duration/1000) + "s");
    }

    public static void searchText(String query, int pagesize){
        File file = new File("heap" + "." + pagesize);
        
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[pagesize];
            
            String in;
            int pageCount = 0;
            int count = 0;
            
            while(is.read(buffer) != -1){
                in = new String(buffer);
                
                System.out.print("Searching in page " + pageCount + "\r");
                
                String[] page = in.split("\r\n");
                
                for(int j=0; j<page.length-1; j++){
                    String[] token = page[j].split("\t");
                    
                    for(int i=0; i<token.length; i++){
                        //split the datainto 2 part
                        String[] data = token[i].split(":",2);
                        
                        //at this point, only search in BN_NAME
                        if(data[0].equals("BN_NAME")){
                            String check1 = query.toLowerCase();
                            String check2 = data[1].toLowerCase();
                            
                            if(check2.contains(check1)){
                                System.out.print("Found '" + query + "' in page " + pageCount + ": ");
                                System.out.print(data[1] + "\r\n");
                                count++;
                            }
                        }
                    }
                }
                pageCount++;
            }
            
            System.out.println();
            System.out.println("Finish loading.");
            System.out.println(count + " result(s) found");
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    
}
