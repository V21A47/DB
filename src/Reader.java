import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

class Reader{
    private String aliveCreaturesFile;
    private String aquatoriesFile;
    private String mineralsFile;
    private String objectsListFile;
    private String locationsFile;
    private String ingredientsFile;

    public Reader(){

        this.mineralsFile  = "data/aliveCreatures";
        this.aquatoriesFile = "data/aquatories";
        this.aliveCreaturesFile = "data/minerals";
        this.objectsListFile = "data/catalog";
        this.locationsFile = "data/locationFile";
        this.ingredientsFile = "data/ingredients";

    }

    public void work(){
        //clearFile(objectsListFile);
        //int amount = fillObjectsListFile();

        //clearFile(objectsListFile+"_new");
        //changeCatalog();
        fillLocationsFile(11740);


    }

    public void changeCatalog(){
        try{
            LineNumberReader lineReader = new LineNumberReader(new FileReader(objectsListFile));
            FileWriter writer = new FileWriter(objectsListFile + "_new", true);

            String line = lineReader.readLine();

            int a = 1;
            int b = 1;

            while(line != null){
                if(line.equals("TRUE")){
                    writer.write(a+ "\t" + "TRUE\n");
                    a+=1;
                } else {
                    writer.write(b+ "\t" + "FALSE\n");
                    b+=1;
                }
                line = lineReader.readLine();
            }

            lineReader.close();
            writer.close();
        } catch (IOException e){
            System.err.println(e);
        }
    }

    // vse
    public void chanceIngredients(){
        try{
            LineNumberReader lineReader = new LineNumberReader(new FileReader(ingredientsFile));
            FileWriter writer = new FileWriter(ingredientsFile + "_new", true);

            String line = lineReader.readLine();

            while(line != null){
                String outText = line + "\t" + (int)(Math.random()*11780) + "\t" + (int)(Math.random()*5000);
                writer.write(outText+"\n");
                line = lineReader.readLine();
            }

            lineReader.close();
            writer.close();
        } catch (IOException e){
            System.err.println(e);
        }

    }

    public void clearFile(String fileName){
        try{
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int fillLocationsFile(int objectsListFileLinesAmount){
        //ид_акватории    ид_существа количество  широта  долгота

        try{
            LineNumberReader lineAquatoryReader = new LineNumberReader(new FileReader(aquatoriesFile));
            LineNumberReader lineObjectsListReader = new LineNumberReader(new FileReader(objectsListFile));

            FileWriter writer = new FileWriter(locationsFile, true);

            String acuatoryLine = lineAquatoryReader.readLine();
            int nulberOfAll = 0;
            int aquatoryId = 1;

            while(acuatoryLine != null){
                String type = acuatoryLine.substring(0, acuatoryLine.indexOf("\t"));

                // Pass type
                acuatoryLine = acuatoryLine.substring(acuatoryLine.indexOf("\t")+1);

                // Pass name
                String name = acuatoryLine.substring(0, acuatoryLine.indexOf("\t"));

                acuatoryLine = acuatoryLine.substring(acuatoryLine.indexOf("\t")+1);

                double x = Double.parseDouble(acuatoryLine.substring(0, acuatoryLine.indexOf("\t")));

                // Pass x
                acuatoryLine = acuatoryLine.substring(acuatoryLine.indexOf("\t")+1);

                double y = Double.parseDouble(acuatoryLine.substring(0, acuatoryLine.indexOf("\t")));


                System.out.println(type + "   " + name);
                for(int i = 0; i < 500 + (int)(Math.random() * 150); i++){
                    // ид_акватории
                    String output = aquatoryId + "\t";

                    // ид_существа
                    output = output + (int)(1 + Math.random() * objectsListFileLinesAmount) + "\t";

                    output = output + (int)(1 + Math.random()*150000) + "\t";


                    double delta = 5;
                    if(type.equals("залив")){
                        delta = 10;
                    } else if(type.equals("море")){
                        delta = 15;
                    } else if(type.equals("океан")){
                        delta = 35;
                    }

                    if(x+delta>90.0D){
                        delta = 90-x;
                    } else if(x-delta < -90){
                        delta = 90+x;
                    }

                    // широта
                    if(Math.random() > 0.5){
                        output = output + String.format("%.3f", ( x + (Math.random() * delta))) + "\t";
                    } else {
                        output = output + String.format("%.3f", ( x - (Math.random() * delta))) + "\t";
                    }

                    if(y+delta>180.0D){
                        delta = 180-y;
                    } else if(y-delta < -180){
                        delta = 180+y;
                    }

                    // долгота
                    if(Math.random() > 0.5){
                        output = output + String.format("%.3f", ( y + (Math.random() * delta))) + "\n";
                    } else {
                        output = output + String.format("%.3f", ( y - (Math.random() * delta))) + "\n";
                    }

                    writer.write(output);
                }
                acuatoryLine = lineAquatoryReader.readLine();
                aquatoryId += 1;
            }

            writer.close();
            lineAquatoryReader.close();
            lineObjectsListReader.close();

        } catch (IOException e) {
            System.err.println(e);
        }
        return 0;
    }


    public int fillObjectsListFile(){
        // сначала живые, потом минералы. Счет с 1
        // ид_существа          живое

        try{
            LineNumberReader lineReader = new LineNumberReader(new FileReader(aliveCreaturesFile));

            FileWriter writer = new FileWriter(objectsListFile, true);

            int lineNumber = 1;
            while(lineReader.readLine() != null){
                writer.write(lineNumber + "\t" + "true" + "\n");
                lineNumber+=1;
            }

            lineReader = new LineNumberReader(new FileReader(mineralsFile));

            while(lineReader.readLine() != null){
                writer.write(lineNumber + "\t" + "false" + "\n");
                lineNumber+=1;
            }

            writer.close();
            lineReader.close();

            return lineNumber-1;
        } catch (IOException e) {
            System.err.println(e);
        }
        return 0;
    }
}
