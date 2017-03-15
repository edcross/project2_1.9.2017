/* 
 * File:   main.cpp
 * Author: jcarlos2289
 *
 * Created on 23 de octubre de 2016, 15:55
 */

#include <cstdlib>
#include <iostream>
//#include <bits/basic_string.h>
#include <fstream>
//#include <bits/stl_vector.h>
#include <vector>

#include <map>
#include <iosfwd>
//#include <bits/stringfwd.h>
//#include <bits/stl_map.h>
#include <iterator>
//#include <bits/stl_vector.h>
//#include <bits/stl_map.h>
#include <string>
#include <boost/lexical_cast.hpp>
//#include <bits/stl_vector.h>
#include "ImageTags.h"
#include <utility>
#include <iosfwd>
#include <memory>
#include <string>
#include <utility>
#include <vector>

#include <iostream> // std::cout, std::fixed
#include <iomanip>

#include "fileMethods/fileMethods.cpp"

using namespace std;

/*
 * 
 */
void trim(string &s); //line added

std::vector<std::string> split(std::string s, char delim)
{
    std::stringstream ss(s);
    std::string item;
    std::vector<std::string> tokens;
    while (getline(ss, item, delim))
    {
        tokens.push_back(item);
    }
    return tokens;
}

int main(int argc, char **argv)
{
    //string troubleshooting = "";
    std::string li;
    std::ifstream arquitectura("arquit.txt");
    while (getline(arquitectura, li))
    { //while superior

        int sq = 5;

        int seqLength = 0;

        //std::string descriptorFolderPath;

        // descriptorFolderPath = "/home/ed/Documents/VidriloTags/Sequence" + boost::lexical_cast<std::string>(sq) + "/Vidrilo_Sequence" + boost::lexical_cast<std::string>(sq) + ".GoogleCloudVision/";
        // descriptorFolderPath = "/home/ed/Documents/VidriloTags/Sequence4/Vidrilo_Sequence4.GoogleCloudVision/";
        std::string descriptorFolderPath = "/home/ed/Documents/VidriloTags/Sequence" + boost::lexical_cast<std::string>(sq) + "/Vidrilo_Sequence" + boost::lexical_cast<std::string>(sq) + li + "/";

        std::string imageListPath = "/home/ed/Dropbox/shared_ED/categorias/Sq" + boost::lexical_cast<std::string>(sq) + ".txt";

        switch (sq)
        {

        case 1:
            // Process for test = 1
            seqLength = 2389;
            break;

        case 2:

            seqLength = 4579;
            break;
        case 3:

            seqLength = 2248;
            break;

        case 4:

            seqLength = 4826;
            break;

        case 5:
            // Process for test = 5
            seqLength = 8412;
            break;
        default:
            // Process for all other cases.
            printf("Error entering value of the sequence number");
            break;
        }

        ImageTags temporalTag(""); //ImageTags("")
        std::vector<ImageTags> imageSeq(seqLength, temporalTag);

        std::string line;
        std::ifstream imageList(imageListPath.c_str());

        if (imageList.is_open())
        {
            int i = 0;
            while (getline(imageList, line))
            {

                std::vector<std::string> splitLine = split(line, '\t');
                string temp = splitLine.at(0);
                trim(temp);
                std::string descriptorFilePath = descriptorFolderPath + temp + ".txt";

                std::ifstream descriptor(descriptorFilePath.c_str());
                std::string tagLine;
                if (descriptor.is_open())
                {

                    imageSeq.at(i).init(temp);
                    imageSeq.at(i).setCategory(splitLine.at(1));

                    imageSeq.at(i).setCategoryNumber(boost::lexical_cast<int>(splitLine.at(2)));
                    while (getline(descriptor, tagLine))
                    {

                        imageSeq.at(i).addTag(tagLine);
                    }
                }
                else
                {
                    std::cout << "Descriptor no abierto" << std::endl;
                    cout << li << endl;
                }

                descriptor.close();

                ++i;

            } //end while image list
            imageList.close();
        }
        else
            std::cout << "No se abrio la lista de los txt" << std::endl;

        std::vector<std::string> keyList = imageSeq.at(0).getKeysVector();
        std::stringstream fileKeyList;
        std::stringstream csvFile;

        csvFile << "Category";
        //ver los keys
        for (std::vector<std::string>::iterator it = keyList.begin(); it != keyList.end(); ++it)
        {

            // dd << "Image " << i + 1 << " " << (*it).tags.size() << " categoria " << (*it).getCategory() << "\n";
            fileKeyList << (*it) << "\n";
            // std::cout << (*it) << std::endl;
            //++i;
            csvFile << "," << (*it);
        }

        csvFile << "\n";

        //fileMethods::saveData("KeyList_500", fileKeyList.str(), false);

        //guardar los valores de train
        //std::stringstream trainDataString;
        int i = 0;
        for (std::vector<ImageTags>::iterator it = imageSeq.begin(); it != imageSeq.end(); ++it)
        {

            int j = 0;

            csvFile << (*it).getClassNumber();
            for (std::vector<std::string>::iterator it2 = keyList.begin(); it2 != keyList.end(); ++it2)
            {
                //std::cout << (*it2)<< std::endl;
                //   x_node[i][j].index = j + 1;
                //  x_node[i][j].value = (*it).tags.at((*it2));
                //std::cout << j <<std::endl;
                //guardar los valores de train
                //trainDataString << j + 1 << ":" << (*it).tags.at((*it2)) << " ";
                csvFile << "," << std::fixed << std::setprecision(8) << (*it).tags.at((*it2));
            }

            ++i;
            csvFile << "\n";
        }
        string fileName = "VidriloSEQ_" + boost::lexical_cast<std::string>(sq) + li + "_CSV";
        fileMethods::saveData(fileName, csvFile.str(), false);

        //end while
        // problem.x = x_node;

        /* //para guardar los valores de los atributos de train
    std::ofstream fs2("TrainVectorData.txt", std::ofstream::out);
    fs2 << trainDataString.str() << std::endl;
    fs2.close();*/

    } //end while
    return 0;
}

void trim(string &s)
{
    while (s.compare(0, 1, " ") == 0)
        s.erase(s.begin()); // remove leading whitespaces
    while (s.size() > 0 && s.compare(s.size() - 1, 1, " ") == 0)
        s.erase(s.end() - 1); // remove trailing whitespaces
}