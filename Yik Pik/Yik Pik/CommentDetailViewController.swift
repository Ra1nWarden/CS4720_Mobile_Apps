//
//  CommentDetailViewController.swift
//  Yik Pik
//
//  Created by Nicholas Milef on 11/16/15.
//  Copyright © 2015 Nick and Zihao. All rights reserved.
//

import UIKit

class CommentDetailViewController: UIViewController {

    @IBOutlet weak var detailTexts: UITextView!
    var author: String? = nil
    var comment: String? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if author != nil {
            self.title = author
        }
        if comment != nil {
            self.detailTexts.text = comment
        }
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
