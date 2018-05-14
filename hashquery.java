import java.io.*;

public class hashquery {

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
        File file = new File("index" + "." + pagesize);
        
        int[] hashData = hash(query);
        boolean bucket = false;
        boolean found = false;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String in;
            
            while((in = br.readLine()) != null && found == false){
                //get bucket
                if(in.charAt(0) == 'p'){
                    if(in.equals("p" + Integer.toString(hashData[0]))){
                        bucket = true;
                        System.out.println("Searching in bucket no." + hashData[0]);
                    }
                    else{
                        bucket = false; //p + NUMBER != what we want
                    }
                }
                else{
                    //getting data location
                    if(bucket){
                        String[] data = in.split(",");
                        
                        //System.out.println("data[0] : " + data[0]);
                        //System.out.println("hashData[1] : " + hashData[1]);
                        
                        if(Integer.parseInt(data[0]) == hashData[1]){
                            System.out.println("Found \"" + query + "\" at " + data[1]);
                            found = true;
                        }
                    }
                }
            }
            
            if(found == false){
                System.out.println("Unable to found \"" + query + "\". Please try again.");
            }
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static double tableSize = Math.pow(2, 10)-1;
    public static double bucketSize = Math.pow(2, 12)-1;
    public static int[] hash(String query){
        int[] index = new int[2];
        int temp =  (int)Math.abs(query.hashCode() % bucketSize);
        
        index[0] = (int)(temp % tableSize);
        //System.out.println(" : 2nd hash: " + index[0]);
        index[1] = Math.abs(query.hashCode());
        
        return index;
    }
}
