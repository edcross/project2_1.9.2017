/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readtags;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos2289
 */
public class ReadTags {

    /**
     * @param args the command line arguments Recibe un txt que contiene las
     * rutas a los archivos de Tags con los grupos y el nombre del grupo crea 2
     * archivos 1 con todas los tags detectados y otros con todos los tags
     * detectados y su frecuencia de aparcion en el grupo de imagenes.
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 0) {
            System.err.println("Please pass 2 arguments. [Group.txt GroupName]");
            return;
        }
        List<String> filePaths = new ArrayList<>();
        String groupName = "";

        filePaths = processFile(args[0]);
        groupName = args[1];

        int g = 0;
        int filesTotal = filePaths.size();
        List<String> tags = new ArrayList<>();
        System.out.println("Reading Files...");

        for (Iterator<String> iterator = filePaths.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            //System.out.println(next);
            List<String> seqPath = new ArrayList<>();
            seqPath = processFile(next);

//            if (seqPath.size() > 0) {
//                System.out.println(seqPath.get(0));
//            }
            String[] temp;
            String linea;
            String delimiter = "-";

            for (int i = 0; i < seqPath.size(); i++) {
                linea = seqPath.get(i);
                temp = linea.split(delimiter);
                tags.add(temp[0]);
            }
            double x = 0;
            x = g * 100 / filesTotal;
            printProgBar((int) Math.floor(x) + 1);
            ++g;
        }

        Collections.sort(tags);
        System.out.println("\n\nSaving Tags File...");
        String cadTxt = "";

        double x = 0;
        g = 0;
        filesTotal = tags.size();

        for (Iterator<String> iterator = tags.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            cadTxt += next + "\n";
            x = g * 100 / filesTotal;
            printProgBar((int) Math.floor(x) + 1);
            ++g;
        }
        cadTxt = "";

        //saveFile(cadTxt, groupName.trim() + "_TagLists");
        List<String> probs = new ArrayList<>();
        String dataCount = "";

        List<Tag> tagProb = new ArrayList<>();
        List<String> sortTag = new ArrayList<>();

        Set<String> data = new HashSet<>(tags);

        int val;
        for (String key : data) {
            //System.out.println(key + " : " + Collections.frequency(tags, key));
            //dataCount += key + "-" + Collections.frequency(tags, key) + "\n";
            val = Collections.frequency(tags, key);
            
            //probs.add(key + "-" + Collections.frequency(tags, key) + "\n");
            sortTag.add(key);
            probs.add(key + "-" + val + "\n");
            tagProb.add(new Tag(key, val));
        }

        // saveFile(dataCount, groupName.trim()+"_DataCountUnSort");
        //Guardar los tags  en orden alfabetico sin la frecuencia
        
        Collections.sort(sortTag);
        
        for(String dat: sortTag){
            cadTxt+= dat +"\n";
        }
        
        
        saveFile(cadTxt, groupName.trim() + "_TagLists");

        //Lista de tag con cantidad ordenada alfabeticamente
        Collections.sort(probs);
        dataCount = "";
        for (String next : probs) {
            dataCount += next;
        }
        saveFile(dataCount, groupName.trim() + "_DataCount");

        //Lista de tag con cantidad ordenada por mayor aparicion
        Collections.sort(tagProb, new Comparator<Tag>() {

            public int compare(Tag t1, Tag t2) {
                // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                return new Integer(t2.getCount()).compareTo(t1.getCount());
            }

        });

        dataCount = "";
        for (Tag next : tagProb) {
            dataCount += next.print();
        }
        saveFile(dataCount, groupName.trim() + "_DataCountSort");

        System.out.println("\nFinish Saving Tags File for the " + groupName + " group.");

    }//final  /home/jcarlos2289/NetBeansProjects/readTags/ListadoSeq.txt

    public static List<String> processFile(String file) {
        //List<String> lista = new ArrayList<>();
        String filepath = file;

        File archivo0 = null;
        FileReader fr = null;
        BufferedReader br = null;

        List<String> paths = new ArrayList<>();
        String path = filepath;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo0 = new File(path);
            fr = new FileReader(archivo0);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;

            while ((linea = br.readLine()) != null) {
                // System.out.println(linea);
                paths.add(linea.trim()); // path de cada archivo de texto
            }
            //System.out.println(String.valueOf("Cantidad de Lineas: " + paths.size()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }

        return paths;

    }

    public static void printProgBar(int percent) { //recibe el valor en formato de porcentaje.
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 50; i++) {
            if (i < (percent / 2)) {
                bar.append("=");
            } else if (i == (percent / 2)) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        System.out.print("\r" + bar.toString());
    }

    public static void saveFile(String cadTxt, String txtname) {
        //This method save a file with the results of the processes of the file with tags

        //obtener la ruta de donde esta ubicado el proyecto
        // String txtname = "tagList";
        File miDir = new File(".");
        String c = miDir.getAbsolutePath();

        //elimino el punto (.) nombre del archivo(virtual) que cree para obtener la ruta de la carpeta del proyecto
        String ruta = c.substring(0, c.length() - 1);

        ruta += "resultados/" + txtname + ".txt";

        //JOptionPane.showMessageDialog(rootPane, ruta);
        File archivo = new File(ruta);

        BufferedWriter bw = null;

        if (archivo.exists()) {
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                try {
                    bw.write(cadTxt);  //archivo existe se escribe en él
                } catch (IOException ex) {
                    Logger.getLogger(ReadTags.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadTags.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(cadTxt); //archivo no existe se crea y se escribe en él
            } catch (IOException ex) {
                Logger.getLogger(ReadTags.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            bw.close(); // se cierra el archivo
        } catch (IOException ex) {
            Logger.getLogger(ReadTags.class.getName()).log(Level.SEVERE, null, ex);
        }

        //cadResultado = "";
        //cadTxt = "";
    }

}
