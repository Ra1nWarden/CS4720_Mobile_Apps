//
//  WelcomeViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 21/10/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit

class WelcomeViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var userNameField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad();
        userNameField.delegate=self;
        passwordField.delegate=self;
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
    }


    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder();
        return true;
    }
}
