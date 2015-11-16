//
//  ReplyViewController.swift
//  Yik Pik
//
//  Created by WangZihao on 11/15/15.
//  Copyright © 2015 Nick and Zihao. All rights reserved.
//

import UIKit
import Parse

class ReplyViewController: UIViewController {

    var item: PFObject? = nil
    @IBOutlet weak var commentField: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Comment"
        commentField.selectAll(nil)
        commentField.layer.borderWidth = 5.0
        commentField.layer.borderColor = UIColor.blackColor().CGColor
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func alert(title: String, content: String) {
        let alert = UIAlertView()
        alert.title = title
        alert.message = content
        alert.addButtonWithTitle("Ok")
        alert.show()
    }

    @IBAction func submit(sender: AnyObject) {
        let comment = commentField.text
        if comment.isEmpty {
            alert("Submit failure", content: "Comment cannot be empty")
        } else {
            let new_comment = PFObject(className: "Comment")
            new_comment["content"] = commentField.text
            new_comment["author"] = PFUser.currentUser()
            new_comment.saveInBackgroundWithBlock {
                (succeeded: Bool, error: NSError?) -> Void in
                if error == nil && succeeded {
                    let relation = self.item?.relationForKey("comments")
                    relation?.addObject(new_comment)
                    self.item?.saveInBackgroundWithBlock{
                        (succeeded: Bool, error: NSError?) -> Void in
                        if error == nil && succeeded {
                            self.alert("Post status", content: "Success!")
                            self.navigationController!.popViewControllerAnimated(true)
                        } else {
                            self.alert("Post status", content: "Network error!")
                        }
                    }
                } else {
                    self.alert("Post status", content: "Network error!")
                }
            }
        }
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
