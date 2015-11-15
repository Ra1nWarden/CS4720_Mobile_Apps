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
        self.navigationItem.setHidesBackButton(true, animated: true)
        self.navigationItem.title = "Yik Pik"
    }

}
