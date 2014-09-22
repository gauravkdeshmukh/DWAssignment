package dwassignment2;

/**
 * Data Warehouses 
 * COSC 5376
 * Assignment #2 : ETL Program in Java
 * @author Gaurav Deshmukh
 */

// all required imports
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DWAssignment2 {

    // input file is the data file provided
    final static String inputFileName = "hw2 data.txt";
    
    // tab is the delimeter for every column in the text
    // \t is regular expression for tab
    final static String DELIM = "\t"; 
    
    /**
     * Function to create an insert script
     */
public void generateInsertscript(){
        FileReader inputFileReader = null;
        
            FileOutputStream fout = null;
            // Structures to store data
            // Tokenizer class object
            StringTokenizer st = null;
            // stores column name from first line
            String Data;
            //flag
            boolean flag;
            // stores the statement for insert script
            String Insertsrcipt;
           
        try {        
            // object of FileReader class to read inputfile
            inputFileReader = new FileReader(inputFileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            // object of BufferedReader class to temporary store the read data
            //  by FileReader class object
            BufferedReader inputStream   = new BufferedReader(inputFileReader);
        try {            
            // FileOuputStream object to fetch insert statements from buffer
            fout = new FileOutputStream("insert.sql");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
            // OuputStreamWriter object to write insert statements into insert.sql file
            OutputStreamWriter out = new OutputStreamWriter(fout);
            // BufferedWriter object to temporary store insert statements
            BufferedWriter writer = new BufferedWriter(out);
            // to store each line got from input      
            String Line = null;
        try {
            // first line is to be ignored
            // As it contains schema of the given table
            Line = inputStream.readLine();
        } catch (IOException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while((Line = inputStream.readLine()) != null){
        try {
            Insertsrcipt = "\nINSERT INTO DATABASE VALUES(";
            // StringTokenizer object , which splits each line into various
            // tokens on reaching DELIM , which is tab in our case
            st = new StringTokenizer(Line,DELIM);
            // flag checks if the first column is inserted
            flag = true;
            while(st.hasMoreElements()){
            Data = st.nextToken();
            // As the data file provided has redundancy to insert data into 
            // database schema , we need to cleanse the data before insertion
            // ' in Oracle is string delimeter , so if any string has ' could
            // give problems in insertion
            // Therefore redundant characters like ' , & , . are checked and
            // replaced by vaild characters
            // for every token checks if string contains any of the invalid 
            // characters
            if(Data.contains("'"))
            {
               Data=Data.replace("'", "''");
               //'' escapes the singlequoute and continues reading the string 
               // as concatenated string.
            }
            
            if(Data.contains("&"))
            {
               Data=Data.replace("&", "'||'&'||'");
               //'||'&'||' concatenates & as the character with preceding and
               // succeding sequence of characters
            }
            // ^[\\.]$ is regular expression matching single dot
            if(Data.matches("^[\\.]$"))
            {
             // . is given as null value in some columns , so rather than having 
             // null in column containing numbers , it is better to take it as 0
                Data=Data.replace(".","0");
            }
            
            if(flag)
                {
                    Insertsrcipt = Insertsrcipt + "'"+ Data +"'";
                    flag=false;
                    // is set to false when first column is inserted
                }
            else
            Insertsrcipt = Insertsrcipt + ","+"'"+Data+"'"; 
            }  
     Insertsrcipt = Insertsrcipt + ");";
     //complete script is wriiten to file
     writer.write(Insertsrcipt);
        } catch (IOException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
        // close input/output streams     
        writer.close();
        fout.close();
        inputFileReader.close();
        inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public void generateCreatescript(){
        FileOutputStream fout = null;
        try {
            // Structures to store data
            // Tokenizer class object
            // st to tokenize schema part 
            StringTokenizer st = null;
            // st2 to tokenize data part
            StringTokenizer st2 = null;
            // stores column name from first line
            // stores data from second line
            // dtores datatype based upon data
            String Column, Data, DataType;
            //flag
            boolean flag = true;
            // stores the partial statement for create script
            String Createsrcipt;
            Createsrcipt = "CREATE TABLE DATABASE(";
            try{
                // object of FileReader class to read inputfile
                FileReader inputFileReader = new FileReader(inputFileName);
             // object of BufferedReader class to temporary store the read data
             //  by FileReader class object
                BufferedReader inputStream   = new BufferedReader(inputFileReader);
                // stores firstline
                String firstLine = null;
                // stores secondline
                String secondLine = null;
                // fetches first and second lines from BufferedReader object
                firstLine = inputStream.readLine();
                secondLine = inputStream.readLine();
             // StringTokenizer object , which splits each line into various
            // tokens on reaching DELIM , which is tab in our case
                    st = new StringTokenizer(firstLine,DELIM);
                    st2 = new StringTokenizer(secondLine,DELIM);
                    // while all tokens for first and secondline are not
                    // exhausted
                    while(st.hasMoreElements() && st2.hasMoreElements()){
                        // put each token as column name
                        // & data to compare fro further analysis
                        Column = st.nextToken();
                        Data = st2.nextToken();
                    //  \\d+.\\d+ is a regular expression for numbers
                    // check if data mataches numbers; set data type to NUMBER
                    // else to VARCHAR(100)    
                    if((Data.matches("\\d+.\\d+"))||(Data.matches("\\d")))
                    {
                      DataType="NUMBER"; 
                    }
                    else
                       DataType="VARCHAR2(50)";
                   // flag checks if the first column is created
                    if(flag)
                        {
                            Createsrcipt = Createsrcipt + "\n" + Column +" "+ DataType;
                            flag = false;
                        }
                    else
                    Createsrcipt = Createsrcipt + ",\n" + Column +" "+ DataType; 
             
                     }
                    
                    } catch (FileNotFoundException ex) {
                Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
            }
            Createsrcipt = Createsrcipt + "\n" + ");";
            // prints the create script
            System.out.println(Createsrcipt);            
           // FileOuputStream object to fetch insert statements from buffer 
            fout = new FileOutputStream("create.sql");
            // OuputStreamWriter object to write insert statements into create.sql file
            OutputStreamWriter out = new OutputStreamWriter(fout);
            // BufferedWriter object to temporary store insert statements
            BufferedWriter writer = new BufferedWriter(out);
            
                try {
                    // write the create statements to create.sql file
                    writer.write(Createsrcipt);
                } catch (IOException ex) {
                    Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    // close writer
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
finally {
            try {
                // close input/output streams
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(DWAssignment2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
  }

    public static void main(String[] args){
        // main program object
        DWAssignment2 obj = new DWAssignment2();
        // calling generateCreatescript function
        obj.generateCreatescript();
        System.out.println("\n CREATE.SQL generation success!");
        // calling generateCreatescript function
        obj.generateInsertscript();
        System.out.println("\n INSERT.SQL generation success!");
 }
}
