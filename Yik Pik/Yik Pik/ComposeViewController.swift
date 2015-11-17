//
//  ComposeViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 11/11/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit
import Parse

class ComposeViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate,
    UITextFieldDelegate {

    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var titleField: UITextField!
    
    var locationManager: CLLocationManager!;
    
    override func viewDidLoad() {
        super.viewDidLoad()
        titleField.delegate = self
        setUpLocationManager()
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationItem.setHidesBackButton(true, animated: true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func takePhoto(sender: AnyObject) {
        let controller : UIImagePickerController = UIImagePickerController()
        controller.sourceType = UIImagePickerControllerSourceType.Camera
        controller.delegate = self
        self.presentViewController(controller, animated: true, completion: nil)
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String: AnyObject]) {
        picker.dismissViewControllerAnimated(true, completion: nil);
        if info[UIImagePickerControllerOriginalImage] is UIImage {
            image.image=info[UIImagePickerControllerOriginalImage] as? UIImage;
        }
    }

    @IBAction func submitPik(sender: AnyObject) {
        let enteredTitle = titleField.text
        if enteredTitle!.isEmpty {
            alert("Empty title", content: "Please give it a title.")
            return
        }
        if image.image == nil {
            alert("Empty photo", content: "Please take a shot.")
            return
        }
        if !CLLocationManager.locationServicesEnabled() {
            alert("Location service error", content: "Location service is not enabled.")
            return
        }
        if CLLocationManager.authorizationStatus() == CLAuthorizationStatus.Denied {
            alert("Location service denied", content: "Please allow Yik Pik to use location service.")
            return
        }
        let pik = PFObject(className: "Pik")
        pik["author"] = PFUser.currentUser()
        pik["latitude"] = locationManager.location!.coordinate.latitude
        pik["longitude"] = locationManager.location!.coordinate.longitude
        pik["title"] = titleField.text!
        let imageData = UIImageJPEGRepresentation(image.image!, 0.7)
        let imageFile = PFFile(name:"image.png", data:imageData!)
        pik["photo"] = imageFile
        pik.saveInBackgroundWithBlock{
            (succeeded: Bool, error: NSError?) -> Void in
            if error == nil && succeeded {
                self.alert("Post status", content: "Success!")
                self.titleField.text = ""
                self.image.image = nil
                self.tabBarController!.selectedIndex = 0
            } else {
                self.alert("Post status", content: "Network error!")
            }
        }
    }
    
    func alert(title: String, content: String) {
        let alert = UIAlertView()
        alert.title = title
        alert.message = content
        alert.addButtonWithTitle("Ok")
        alert.show()
    }
    
    func setUpLocationManager() {
        locationManager = CLLocationManager();
        locationManager.requestAlwaysAuthorization();
        locationManager.desiredAccuracy = kCLLocationAccuracyBest;
        locationManager.startUpdatingLocation();
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
