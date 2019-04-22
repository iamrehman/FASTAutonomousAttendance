import numpy as np 
import os 
import re 
import face_recognition as fr 
import sys 

def image_files_in_folder(folder):
    """
    this function returns names of image files in a folder 
    """
    return [os.path.join(folder, f) for f in os.listdir(folder) if re.match(r'.*\.(jpg|jpeg|png)', f, flags=re.I)]


def getMin(images, tocheck_encodings):
    """returns image closest to given image"""
    distance= fr.face_distance(images,tocheck_encodings)
    return np.argmin(distance)

#images to check 
testImages=image_files_in_folder("C:\\Users\\iamrehman\\Pictures\\Camera Roll")

#images already stored
TrainedImages=image_files_in_folder("D:\\workspace\\FASTAutonomousAttendance\\faceRecognition\\data")

Timages=[]
for names in TrainedImages:
    base=os.path.basename(names)
    Timages.append( os.path.splitext(base)[0])

Timages=np.array(Timages)
saved_feats=np.load("D:\\workspace\\FASTAutonomousAttendance\\faceRecognition\\FAA_Encodings.npy")

result=[]

print ("test Images:")
print (testImages)

for image in testImages:
    img=fr.load_image_file(image)
    #get encodings
    feats=None 
    try:
        feats=fr.face_encodings(img)[0]
    except:
        print ("no face found in", image )
        continue

    matches= fr.compare_faces(saved_feats,feats, tolerance=.60)
    matches=np.array(matches)
    index= np.ma.where(matches==True)
  
    if np.ma.any(index)==False :
        result.append("Unknown Face")
        continue

    val=Timages[index]   
       
    (values,counts) = np.unique(val,return_counts=True)
    if(len(values)>1):
        min_index=getMin(saved_feats[index],feats)
        result.append(values[min_index])
    else:
         result.append(values[0])    

out_file=open("D:\\workspace\\FASTAutonomousAttendance\\faceRecognition\\result.txt",'w')
for id in result:
    out_file.write(id+"\n")
out_file.close()






