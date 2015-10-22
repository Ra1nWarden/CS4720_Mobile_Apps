//
//  UserInfoViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 21/10/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit

class UserInfoViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    @IBOutlet weak var messageLabel: UILabel!
    
    var userName : String = ""
    
    @IBAction func takePhoto(sender: AnyObject) {
        let controller : UIImagePickerController = UIImagePickerController()
        controller.sourceType = UIImagePickerControllerSourceType.Camera
        controller.delegate = self
        self.presentViewController(controller, animated: true, completion: nil)
    }

    override func viewDidLoad() {
    
        super.viewDidLoad()
        messageLabel.text = userName
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
