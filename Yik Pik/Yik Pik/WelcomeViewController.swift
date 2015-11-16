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
        if PFUser.currentUser() != nil {
            performSegueWithIdentifier("login", sender: nil)
        }
        userNameField.delegate=self;
        passwordField.delegate=self;
        
        lm=CLLocationManager();
        lm.delegate=self;
        lm.requestAlwaysAuthorization();
        lm.desiredAccuracy=kCLLocationAccuracyBest;
        lm.startUpdatingLocation();
        
        let savedUsername = NSUserDefaults.standardUserDefaults().stringForKey(keyForUsername);
        if (savedUsername != nil) {
            userNameField.text = savedUsername
        }
        
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        passwordField.text = ""
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
     // Dispose of any resources that can be recreated.
    }
    
    override func shouldPerformSegueWithIdentifier(identifier: String, sender: AnyObject?) -> Bool {
        if (identifier == "login") {
            let inputUsername = userNameField.text
            let inputPassword = passwordField.text
            if (inputUsername!.isEmpty || inputPassword!.isEmpty) {
                let alert = UIAlertView()
                alert.title = "Missing fields"
                alert.message = "Please enter your login credentials!"
                alert.addButtonWithTitle("Ok")
                alert.show()
            }
            PFUser.logInWithUsernameInBackground(inputUsername!, password: inputPassword!) {
                (user: PFUser?, error: NSError?) -> Void in
                    if user != nil {
                        // Do stuff after successful login.
                        self.performSegueWithIdentifier("login", sender: nil)
                    } else {
                        // The login failed. Check error to see why.
                        let alert = UIAlertView()
                        alert.title = "Login failure"
                        alert.message = "Please check your username and password!"
                        alert.addButtonWithTitle("Ok")
                        alert.show()
                    }
            }
            return false;
        }
        return true;
    }


    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "login") {
            let inputText = userNameField.text
            NSUserDefaults.standardUserDefaults().setObject(inputText, forKey: keyForUsername)
        }
    }


    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder();
        return true;
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        latText.text="Lat: "+String(locations[locations.count-1].coordinate.latitude);
        longText.text="Long: "+String(locations[locations.count-1].coordinate.longitude);
    }
    
    override func shouldAutorotate() -> Bool {
        return true
    }

    
}
