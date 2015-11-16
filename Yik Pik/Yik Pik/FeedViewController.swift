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

class FeedViewController: PFQueryTableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
        objectsPerPage = 10
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        parseClassName = "Pik"
        pullToRefreshEnabled = true
        paginationEnabled = true
        objectsPerPage = 10
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
            cell?.titleLabel.text = object["title"] as? String
            cell?.pictureView.contentMode = .ScaleAspectFit
            cell?.pictureView.file = object["photo"] as? PFFile
            cell?.pictureView.loadInBackground()
        }
        
        return cell
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
