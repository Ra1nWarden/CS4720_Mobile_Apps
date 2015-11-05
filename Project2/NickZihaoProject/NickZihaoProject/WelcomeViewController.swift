//
//  WelcomeViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 21/10/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit
import CoreLocation
import Parse

class WelcomeViewController: UIViewController, UITextFieldDelegate, CLLocationManagerDelegate {

    @IBOutlet weak var userNameField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var latText: UILabel!
    @IBOutlet weak var longText: UILabel!
    
    var lm: CLLocationManager!;
    let keyForUsername = "username";
    
    override func viewDidLoad() {
        super.viewDidLoad();
        userNameField.delegate=self;
        passwordField.delegate=self;
        
        lm=CLLocationManager();
        lm.delegate=self;
        lm.requestAlwaysAuthorization();
        lm.desiredAccuracy=kCLLocationAccuracyBest;
        lm.startUpdatingLocation();
        
        if (CLLocationManager.locationServicesEnabled()) {
            latText.text="true";
        }
        else {
            latText.text="false";
        }
        
        let data = PFObject(className: "Users");
        data["userName"]="It worked";
        data.saveInBackground();
        
        let savedUsername = NSUserDefaults.standardUserDefaults().stringForKey(keyForUsername);
        if (savedUsername != nil) {
            userNameField.text = savedUsername
        }
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let inputText = userNameField.text
        let destination = segue.destinationViewController as! UserInfoViewController
        destination.userName = inputText!
        NSUserDefaults.standardUserDefaults().setObject(inputText, forKey: keyForUsername)
    }


    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder();
        return true;
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        latText.text="Lat: "+String(locations[locations.count-1].coordinate.latitude);
        longText.text="Long: "+String(locations[locations.count-1].coordinate.longitude);
    }
    
}
