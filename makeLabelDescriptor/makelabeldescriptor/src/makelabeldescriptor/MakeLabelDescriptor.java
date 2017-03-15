/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makelabeldescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author jcarlos2289
 */
public class MakeLabelDescriptor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 0 || args.length < 2) {
            System.err.println("Please pass 2 arguments. [ImageDescriptor.txt OutputDIR]");
            return;
        }

        List<String> tagDataset = new ArrayList<>();
        List<String> tagImage = new ArrayList<>();
        String savePathDir = args[1];
        //String fileName = "";
        //Revisar
        String fileName = args[0].substring(args[0].lastIndexOf("/"), args[0].length() - 4);
        //editar hacia el archov de readtags
        tagDataset = FileMethods.processFile("/home/ed/Documents/Proyecto_2/readTags/resultados/GCVlabels_TagLists.txt");

        tagImage = FileMethods.processFile(args[0]);

        HashMap<String, Double> imageDescriptor = new HashMap<>();

        for (String lab : tagDataset) {
            imageDescriptor.put(lab, 0.0);
        }

        for (String line : tagImage) {
            String temp[] = line.split("-");
            imageDescriptor.put(temp[0], Double.parseDouble(temp[1]));
        }

//        String finalDescriptor = "";
        String finalDescriptor2 = "";

        StringBuilder stringBuilder = new StringBuilder();

        imageDescriptor.forEach((k, v) -> stringBuilder.append(k + "-" + String.valueOf(v)+"\n"));
        finalDescriptor2 = stringBuilder.toString();

//        for (Entry<String, Double> entry : imageDescriptor.entrySet()) {
//            finalDescriptor += entry.getKey() + "-" + String.valueOf(entry.getValue()) + "\n";
//        }
        
        String fullName = savePathDir + fileName;
        //Si no sale bien usa el finalDescriptor que es lo mismo 
        FileMethods.saveFile(finalDescriptor2, fullName, false);

    }

  

}
