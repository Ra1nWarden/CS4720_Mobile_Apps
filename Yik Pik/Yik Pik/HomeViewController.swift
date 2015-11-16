//
//  HomeViewController.swift
//  NickZihaoProject
//
//  Created by Zihao Wang on 11/11/15.
//  Copyright © 2015年 Nick&Zihao. All rights reserved.
//

import UIKit

class HomeViewController: UITabBarController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Yik Pik"
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationItem.setHidesBackButton(true, animated: true)
    }

}
