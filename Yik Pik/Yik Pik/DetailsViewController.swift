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
    var nameMaps: NSMutableDictionary!
    var fav: Bool = false
    
    @IBOutlet weak var favButton: UIButton!
    @IBOutlet weak var photo: PFImageView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if (item != nil) {
            photo.file = item!["photo"] as? PFFile
            photo.loadInBackground()
            self.title = item!["title"] as? String
        }
        if fav {
            favButton.setBackgroundImage(UIImage(named: "favorited"), forState: .Normal)
        } else {
            favButton.setBackgroundImage(UIImage(named: "favorite"), forState: .Normal)
        }
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
        
        if nameMaps == nil {
            nameMaps = NSMutableDictionary.init(capacity: 100)
        }
        
        var cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier) as? PFTableViewCell
        if cell == nil {
            cell = PFTableViewCell(style: UITableViewCellStyle.Value1, reuseIdentifier: cellIdentifier)
        }
        
        // Configure the cell to show todo item with a priority at the bottom
        if let object = object {
            let user = object["author"] as? PFUser
            let userId = user?.objectId
            var userName = nameMaps!.objectForKey(userId!) as? String
            if userName == nil {
                userName = Randoms.randomFakeName()
                nameMaps!.setObject(userName!, forKey: userId!)
            }
            cell!.textLabel?.text = userName
            cell!.detailTextLabel?.text = object["content"] as? String
            cell!.textLabel?.font = UIFont(name: "Quicksand-Bold", size: 15)!
            cell!.detailTextLabel?.font = UIFont(name: "Quicksand-Regular", size: 12)!;
        }
        
        return cell
    }
    
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        performSegueWithIdentifier("comment_detail", sender: nil)
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
        if segue.identifier == "comment_detail" {
            let destination = segue.destinationViewController as? CommentDetailViewController
            let selectedObject = self.objectAtIndexPath(self.tableView!.indexPathForSelectedRow)
            let user = selectedObject?["author"] as? PFUser
            let userId = user?.objectId
            destination?.author = nameMaps.objectForKey(userId!) as? String
            destination?.comment = selectedObject?["content"] as? String
        }
    }
    
    @IBAction func favPressed(sender: AnyObject) {
        if item == nil {
            return
        }
        if fav {
            favButton.setBackgroundImage(UIImage(named: "favorite"), forState: .Normal)
            fav = false
            let user = PFUser.currentUser()
            let relation = user?.relationForKey("favorites")
            relation?.removeObject(item!)
            user?.saveInBackground()
        } else {
            favButton.setBackgroundImage(UIImage(named: "favorited"), forState: .Normal)
            fav = true
            let user = PFUser.currentUser()
            let relation = user?.relationForKey("favorites")
            relation?.addObject(item!)
            user?.saveInBackground()
        }
    }

    @IBAction func share(sender: AnyObject) {
        let message = photo.image;
        let controller=UIActivityViewController(activityItems: [message!], applicationActivities: nil);
        presentViewController(controller, animated: true, completion: nil);
    }
}
