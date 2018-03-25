import java.io.*;

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
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			
			String in;
			int page = 0;
			String[] header = (br.readLine()).split("\t"); //read pass the header
			
            pw.println("page 0");
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
                    pw.print(data);
					page += b.length;
				}
				else{
                    System.out.print("Writing page " + pageNum + ". Previous page size is " + page + "\r");
                    pw.println("page " + pageNum);
                    pw.print(data);
					page = b.length;
					pageNum++;
				}
			}
			br.close();
			pw.close();
            
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
}
