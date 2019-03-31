import numpy as np 
import face_recognition as fr
import os
import re
import sys 

def image_files_in_folder(folder):
    """
    this function returns names of image files in a folder 
    """
    return [os.path.join(folder, f) for f in os.listdir(folder) if re.match(r'.*\.(jpg|jpeg|png)', f, flags=re.I)]


def read_images_from_folder(folder):
    """
    input -> folder to read images from 
    returns list of images 
    """

    #creating an empty list of images
    images_list=[]

    #listing files names
    file_names=image_files_in_folder(folder)

    #reading images from using file's name
    for files_name in file_names:
        images_list.append(fr.load_image_file(files_name))

    return images_list

def get_face_encodings(images):
    """
    input-> image files
    returns a list of face encodings for given images 
    """
    #creating feature vector for each image
    image_features=[]

    #getting features of each image 
    for image in images:

        #getting econdings
        features=None
        try:  
            features=fr.face_encodings(image)[0]
        except:
             print ("face not found ", image )

        #appending to features list 
        image_features.append(features)
    return np.array(image_features)

print ("reading images.....")

#getting images data

if (len(sys.argv)>1):
    #get arguments from command line 
    images=read_images_from_folder(sys.argv[1])
else :#default 
    images=read_images_from_folder("data")

print("training model....")

#getting encodings
feats= get_face_encodings(images)
np.save("FAA_encodings",feats)
print("saving features to file 'FAA_eoncodings' ....")
#saving face features 
np.save("FAA_eoncodings",feats)

print("model trained successfully....")