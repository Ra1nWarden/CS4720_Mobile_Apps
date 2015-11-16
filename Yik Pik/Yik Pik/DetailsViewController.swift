//
//  DetailsViewController.swift
//  Yik Pik
//
//  Created by WangZihao on 11/15/15.
//  Copyright © 2015 Nick and Zihao. All rights reserved.
//

import UIKit
import Parse
import ParseUI
import SwiftRandom

class DetailsViewController: PFQueryTableViewController {
    
    var item: PFObject? = nil
    var nameMaps: NSMutableDictionary?
    
    @IBOutlet weak var photo: PFImageView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if (item != nil) {
            photo.file = item!["photo"] as? PFFile
            photo.loadInBackground()
            self.title = item!["title"] as? String
        }
        
        nameMaps = NSMutableDictionary?.init()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override init(style: UITableViewStyle, className: String?) {
        super.init(style: style, className: className)
        parseClassName = "Comment"
        pullToRefreshEnabled = true
        paginationEnabled = true
        objectsPerPage = 10
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        parseClassName = "Comment"
        pullToRefreshEnabled = true
        paginationEnabled = true
        objectsPerPage = 10
    }
    
    override func queryForTable() -> PFQuery {
        let query = item?.relationForKey("comments").query()
        
        // If no objects are loaded in memory, we look to the cache first to fill the table
        // and then subsequently do a query against the network.
        if self.objects!.count == 0 {
            query!.cachePolicy = .CacheThenNetwork
        }
        
        query!.orderByDescending("createdAt")
        
        return query!
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath, object: PFObject?) -> PFTableViewCell? {
        let cellIdentifier = "cell"
        
        var cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier) as? PFTableViewCell
        if cell == nil {
            cell = PFTableViewCell(style: UITableViewCellStyle.Value1, reuseIdentifier: cellIdentifier)
        }
        
        // Configure the cell to show todo item with a priority at the bottom
        if let object = object {
            let user = object["author"] as? PFUser
            let userId = user?.objectId
            var userName = nameMaps?.objectForKey(userId!) as? String
            if userName == nil {
                userName = Randoms.randomFakeName()
                nameMaps?.setObject(userName!, forKey: userId!)
            }
            cell!.textLabel?.text = userName
            cell!.detailTextLabel?.text = object["content"] as? String
        }
        
        return cell
    }
    

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "comment" {
            let destination = segue.destinationViewController as? ReplyViewController
            destination!.item = item
        }
    }

}
