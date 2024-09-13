<a id="top"></a>

# Estimation Assistant Web Application

<div align="center">
    <img src="src/main/webapp/favicon.ico" alt="Logo" width="80" height="80">
    <h2 align="center">Estimate</h2>
    <p align="center"> Semester project for CSCI 413 - Software Engineering at North Dakota State University </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#overview">Overview</a>
      <ul>
        <li><a href="#purpose">Purpose</a></li>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#features">Features</a></li>
      </ul>
    </li>
    <li><a href="#setup">Setup</a></li>
    <ul>
        <li><a href="#purpose">Prereqs</a></li>
        <li><a href="#setup-steps">Setup Steps</a></li>
      </ul>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributors">Contributors</a></li>
  </ol>
</details>


## Overview

### Purpose

This project is an extension of previous students' work. The intention of this project was to take buggy code with incomplete requirements, elicit a complete set of requirements, and expand functionality while troubleshooting & debugging existing codebase. 

### Built With

* [![Eclipse IDE][Eclipse]][Eclipse-url]
* [![Tapestry][Tapestry]][Tapestry-url]
* [![Cayenne][Cayenne]][Cayenne-url]
* [![Jetty][Jetty]][Jetty-url]
* [![Java][Java]][Java-url]


### Features

#### Account Creation & Login  

*  Enforced password requirements
*  Apache Shiro password authentication & user authorization

![image](https://github.com/user-attachments/assets/4c1ec008-11a3-490e-a24f-496d2f5cca22)


#### Tasks

*  Log time spent on each task (increment buttons or custom)
*  Edit (rename) tasks
*  Close tasks - mark tasks dropped or completed
*  View system estimation vs user estimation for in-progress tasks' total time
    - System estimate is
 
![Tasks](https://github.com/user-attachments/assets/33fb3795-c36d-4632-8d06-811e40cdf8e1)

*  Add tasks
  
![image](https://github.com/user-attachments/assets/f4121069-c4c9-4326-8397-0d84d127fdf6)

*  View hour log history for active tasks

![image](https://github.com/user-attachments/assets/3ef6e21a-4f1e-41be-a254-05f4dd6741e1)

*  View closed tasks

  ![image](https://github.com/user-attachments/assets/3ce4f50f-9b6c-40ea-93f9-ae3b59a54a33)


#### Reports

![image](https://github.com/user-attachments/assets/7cd1a622-ab5d-43bb-8862-4bf8f7e1ab40)

*  System estimation updates as new reports are generated & average ratio is recalculated

![image](https://github.com/user-attachments/assets/0b04055b-00a3-4aa8-8975-06df812316e5)


#### Estimation Calibration

![image](https://github.com/user-attachments/assets/40798df2-6baf-481d-885e-53d5dc1f660f)


## Setup

### Prerequisites

Eclipse IDE
Java  
Cayenne Modeler  

### Setup Steps

Clone this repository locally using 
  ```sh
  git clone https://github.com/audreytracy/android-pathfinding.git
  ```
Run from Eclipse IDE  
Run as Maven Build  
Goals:
```
jetty:run
```

## Usage


## Roadmap


## Contributors

Collaborator credit Fall 2023: [Quinn Johnson](https://www.linkedin.com/in/quinn-johnson-gskjdhf/), [Michael Faust](https://www.linkedin.com/in/mike-faust-2a6824292/)  
(they are not on this repo because this is a copy of the temporary class repo)

<p align="right">(<a href="#top">back to top</a>)</p>

[product-screenshot]: https://drive.google.com/file/d/1rnBvLvwCp1dNsZXmczlAVDcNG-TPZusb/view?usp=sharing
[Eclipse]: https://img.shields.io/badge/Eclipse_IDE-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white
[Eclipse-url]: https://eclipseide.org/
[Jetty]: https://img.shields.io/badge/Jetty-FC390E?style=for-the-badge&logo=eclipsejetty&logoColor=white
[Jetty-url]: https://jetty.org
[Cayenne]: https://img.shields.io/badge/Apache%20Cayenne-dc5655?style=for-the-badge&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAgCAYAAABQISshAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAASxSURBVHgB1Zh7aNtVFMfPTdstTZsuqX1Y2exkVFE3EVZlWwURpMWJMgRfOESkFcGByFDmQBiK28Q/VNBVhg9Q5wPHplVESkvV/SFaxIoLdNM+7NphN0vz/KVJl1w/v9/SmqadWZqkTQ+cnHvPPb/7O9/fPffccyOygklrXauDwVf1xERFsaxQ0j5fpYRCPaLU9bJq1YCsRNLhcL02jGEdCml4n6xE0pHIJpw/Z4EIh7+RQifivyjh9FWzunC4lX7UAmEYA9g4pRAJxxSbtxknj+NsKBE6k3CbNTY5uR4wJ+iHdSBwgxQi6ampBgB042Q8AWAuG8bnAKnXXq+b/hOpzyspAMLJpxEvSfpQGZDp6Rblcs3LUssKhHC5QgzjCM2WDB4bkXB4m6qqGktWLhsQYvxGsdm+onmNZEpa90lZ2Val1NSMalmAEOO3II7Ba2WxFI/XKqfz3EzXJktMgNjBF+2UbEAwjZSX+5MVSwqE1Poo4ihlhUuyIa1PJoeVSUtWa7ESdyHeg4ske+pKVSzJiiRWwtzYuQAhZLr2VFXegbASTyHelFyBUOqoqqn5Y55a8kiAuBdxXHL1wbT2SUlJk1q92pM6lLcVoSYyy4hDOX2HzbZvIRDWkOSY9NCQi8PuMF/vEfH7b0M1KLmhA8rheP1SgzkFQs20Tqqrv+fLKSktbVZ1dcMcXJ9IthSLvabKyvb+n0lOgFgltmE8TOMEm7Gdl7aR5yPWYHHxO/waslhS6hUZGdmTzixrINydGyjiugDxAexiBfrn+FFaOoz+RcmUzI1tsz1GOO1RGzdG05kv+kDU5887KdwOilm5xuO7+HJRXtxHfzd3ht8otSctu0hkB6HxEI5lMLnuZyXvUXb7n5f7SMYrQgitZTO/LQ7HrwAYZS9sVuXl3zL0Cw6EpKjoef7V2MsheCe238mFC4ex+xC+nfGzaaY3w7FdotEtmYC4fOe1LuEGt51zoQMH/cgnrb9jkm2CwZtwvAe5E/ZhM4Tcza1utq5ipTagO7XgDTAU+hFulHwQjm2DD/DyEeRHgGmxnAwEdqXYNeHEoYRDvfSPWZt/oTm1dmCzf/ZKGwx6kI9LljTvZGfSOnOTEQoP0g3w5g7a76uKin+s8UDgZcZjcBf6ZlT3YXMl/U/ZC78jz8JedG1s1J1z5g4Gb2YvPWCOIf9G9RYh+i4ZblpyBUT7/U1M/hxO3Er3JPwXabTVTK0yPl7N5lsvdvsdjG/HETu29QD5jD3RIR7PD6qx0XKGE93MUIOMvSBe7ybOlQbu2fejuxtex/NfA/gIiaILABlkgDRA+Eq1TP4Gzl1Hv4OXdOJ0HY624sw08lrYRnsQ2YddWMx7s9Y1bPKDyZPpsTGHVFZuZewLuASVmbk07W74S+bunslmuSbzIHuWl+ynPSEXVygGm9mlCsefAaSHsnlc3G6/jI7acdSFvofxnwH3Mc9uYFUakFvQ1SPNKrcXm5/gTolE+pXb7ZU8kxVa1qFWXGzeGTbjSC0OXG0BuUg6wf+lauINm3FAnqI9Svs0afY0lWkvaXNZ/lC+ZBmvz5wplTVrysTptIvPV8LXj7EqEeI+Ii6Xn/iOSwHRv17ftPu1fbzQAAAAAElFTkSuQmCC&logoColor=white
[Cayenne2]: https://cayenne.apache.org/img/logo_mono_full-d7a19eef61
[Cayenne-url]: https://cayenne.apache.org
[Java]: https://img.shields.io/badge/Java-3a75af?style=for-the-badge&logo=coffeescript&logoColor=white
[Java-url]: https://www.java.com
[Tapestry]: https://img.shields.io/badge/Apache_Tapestry-7caf45?style=for-the-badge&logo=apache&logoColor=white
[Tapestry-url]: https://tapestry.apache.org/
