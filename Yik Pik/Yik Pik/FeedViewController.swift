//
//  FeedViewController.swift
//  Yik Pik
//
//  Created by WangZihao on 11/15/15.
//  Copyright © 2015 Nick and Zihao. All rights reserved.
//

import UIKit
import Parse
import ParseUI
import CoreMotion

class FeedViewController: PFQueryTableViewController {

    var motion_manager=CMMotionManager();
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
    
    override init(style: UITableViewStyle, className: String?) {
        super.init(style: style, className: className)
        parseClassName = "Pik"
        pullToRefreshEnabled = true
        paginationEnabled = true
        objectsPerPage = 50
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        parseClassName = "Pik"
        pullToRefreshEnabled = true
        paginationEnabled = true
        objectsPerPage = 50
    }
    
    override func queryForTable() -> PFQuery {
        let query = PFQuery(className: "Pik")
        
        // If no objects are loaded in memory, we look to the cache first to fill the table
        // and then subsequently do a query against the network.
        if self.objects!.count == 0 {
            query.cachePolicy = .CacheThenNetwork
        }
        
        query.orderByDescending("createdAt")
        
        return query
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath, object: PFObject?) -> PFTableViewCell? {
        let cellIdentifier = "feed_cell"
        
        var cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier) as? FeedViewCell
        if cell == nil {
            tableView.registerNib(UINib.init(nibName: "FeedViewCell", bundle: nil), forCellReuseIdentifier: cellIdentifier)
            cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier) as? FeedViewCell
        }
        
        // Configure the cell to show todo item with a priority at the bottom
        if let object = object {
            cell?.titleLabel
            cell?.titleLabel.text = object["title"] as? String
            cell?.pictureView.contentMode = .ScaleAspectFill
            cell?.pictureView.file = object["photo"] as? PFFile
            cell?.pictureView.loadInBackground()
            let query = object.relationForKey("comments").query()
            query!.countObjectsInBackgroundWithBlock {
                (count: Int32, error: NSError?) -> Void in
                cell?.commentView.text = NSString(format: "%d comments", count) as String
            }
        }
        return cell
    }
    
    // MARK: - Navigation
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.performSegueWithIdentifier("details", sender: nil)
    }

    override func motionEnded(motion: UIEventSubtype, withEvent event: UIEvent?) {
        if (motion == .MotionShake) {
            self.loadObjects()
            alert("Refresh",content: "Images refreshed");
        }
    }
    
    func alert(title: String, content: String) {
        let alert = UIAlertView()
        alert.title = title
        alert.message = content
        alert.addButtonWithTitle("Ok")
        alert.show()
    }
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "details" {
            let destinationViewController = segue.destinationViewController as? DetailsViewController
            let selectedObject = self.objectAtIndexPath(self.tableView!.indexPathForSelectedRow)
            destinationViewController!.item = selectedObject
        }
    }

}
