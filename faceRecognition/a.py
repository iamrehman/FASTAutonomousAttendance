import numpy as np 
import face_recognition as fr
import os
import re
import sys 



img=fr.load_image_file("shoaib1.jpeg")
featurs=fr.face_encodings(img)[0]

print (featurs)
