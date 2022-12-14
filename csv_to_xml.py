#!/usr/bin/env python
# coding: utf-8

# In[42]:


import pandas as pd
import glob
import os
import xml.etree.ElementTree as ET
import csv
from xml.dom import minidom


# In[43]:


# PATH TO BE CONFIGURED
CSV_PATH = "done.csv"
DEST_PATH = "test_dest"


# In[44]:


csv_path, csv_file_name = os.path.split(CSV_PATH)


# In[45]:


if csv_path == "" :
    csv_path = os.getcwd()
    full_path = os.path.join(csv_path,csv_file_name)
    if not os.path.isfile(full_path):
        print("please check the path or file location")
        exit()

# Make destin_dir
if not os.path.exists(DEST_PATH) :
                os.mkdir(DEST_PATH)


# In[46]:


def draw_xml(filename_s, width_s, height_s, classname_s, xmin_s, ymin_s, xmax_s, ymax_s):
    # define XML file structure
    root = ET.Element("annotation")
    folder = ET.SubElement(root, "folder")
    filename = ET.SubElement(root, "filename")
    path = ET.SubElement(root, "path")
    source = ET.SubElement(root, "source")
    database = ET.SubElement(source, "database")
    size = ET.SubElement(root, "size")
    width = ET.SubElement(size, "width")
    height = ET.SubElement(size, "height")
    depth = ET.SubElement(size, "depth")
    segmented = ET.SubElement(root, "segmented")

    # assign values (files info)
    foo, folder.text = os.path.split(csv_path)
    filename.text = filename_s
    path.text = csv_path
    foo, database.text = os.path.split(csv_path)
    width.text = width_s
    height.text = height_s
    depth.text = "3"
    segmented.text ="0"

    # draw object
    root = append_object_xml(root, classname_s, xmin_s, ymin_s, xmax_s, ymax_s)

    return root


# In[47]:


def append_object_xml(root, classname_s, xmin_s, ymin_s, xmax_s, ymax_s):
    object_ = ET.SubElement(root, "object")
    name = ET.SubElement(object_, "name")
    pose = ET.SubElement(object_, "pose")
    truncated = ET.SubElement(object_, "truncated")
    difficult = ET.SubElement(object_, "difficult")
    bndbox = ET.SubElement(object_, "bndbox")
    xmin = ET.SubElement(bndbox, "xmin")
    ymin = ET.SubElement(bndbox, "ymin")
    xmax = ET.SubElement(bndbox, "xmax")
    ymax = ET.SubElement(bndbox, "ymax")

    # assign values (object info)
    name.text = classname_s
    pose.text = "Unspecified"
    truncated.text = "0"
    difficult.text = "0"
    xmin.text = xmin_s
    ymin.text = ymin_s
    xmax.text = xmax_s
    ymax.text = ymax_s

    return root


# In[48]:


from xml.dom import minidom

if __name__ == "__main__":
    previous_filename = ""
    csv_file = open(CSV_PATH)
    csv_reader = csv.DictReader(csv_file, delimiter=',')
    xml_root = None
    for row in csv_reader:
        # Creat New
        if row["filename"] != previous_filename :
            # Except first row
            if previous_filename != "":
                filename, ext = os.path.splitext(previous_filename)
                tree = ET.ElementTree(xml_root)
                DEST_FILE_PATH = os.path.join(DEST_PATH,filename+".xml")
                xmlstr = minidom.parseString(ET.tostring(xml_root)).toprettyxml(indent="   ")
                with open(DEST_FILE_PATH, "w") as f:
                    f.write(xmlstr)
            # Draw new XML
            xml_root = draw_xml(row["filename"],row["width"],row["height"],row["class"],row["xmin"],row["ymin"],row["xmax"],row["ymax"])
        # For each object in same file
        if row["filename"] == previous_filename :
            # Add object in XML
            append_object_xml(xml_root, row["class"],row["xmin"],row["ymin"],row["xmax"],row["ymax"])
        
        # Save previous one
        previous_filename = row["filename"]


# In[ ]:





# In[ ]:




