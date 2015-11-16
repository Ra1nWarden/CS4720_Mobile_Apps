//
//  RegisterViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 09/11/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit
import Parse

class RegisterViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var usernameField: UITextField!
    
    @IBOutlet weak var passwordField: UITextField!

    @IBOutlet weak var confirmField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad();
        usernameField.delegate = self
        passwordField.delegate = self
        confirmField.delegate = self
        // Do any additional setup after loading the view.
    }
    
    func alert(title: String, content: String) {
        let alert = UIAlertView()
        alert.title = title
        alert.message = content
        alert.addButtonWithTitle("Ok")
        alert.show()
    }
    
    @IBAction func submitRegistration(sender: AnyObject?) {
        let inputUsername = usernameField.text
        let inputPassword = passwordField.text
        let inputConfirm = confirmField.text
        if inputPassword!.isEmpty || inputUsername!.isEmpty || inputConfirm!.isEmpty {
            alert("Missing fields", content: "Please enter all required information!")
        } else if inputPassword! != inputConfirm! {
            alert("Password does not match", content: "Your password and confirmation fields do not match!")
        } else {
            let user = PFUser()
            user.username = inputUsername!
            user.password = inputPassword!
            user.signUpInBackgroundWithBlock {
                (succeeded: Bool, error: NSError?) -> Void in
                if succeeded {
                    self.navigationController?.popToRootViewControllerAnimated(true)
                    self.alert("Success", content: "Registration succeeded!")
                } else {
                    self.alert("Failure", content: "An error occurred in registration!")
                }
            }
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
}
