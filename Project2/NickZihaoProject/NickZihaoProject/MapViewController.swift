//
//  MapViewController.swift
//  NickZihaoProject
//
//  Created by Nicholas Milef on 10/28/15.
//  Copyright © 2015 Nick&Zihao. All rights reserved.
//

import Foundation
import UIKit
import MapKit
import CoreLocation;

class MapViewController : UIViewController, CLLocationManagerDelegate {

    @IBOutlet var map: MKMapView!
    
    var lm: CLLocationManager!
    
    override func viewDidLoad() {
        super.viewDidLoad();
        
        lm=CLLocationManager();
        lm.delegate=self;
        lm.requestAlwaysAuthorization();
        lm.desiredAccuracy=kCLLocationAccuracyBest;
        lm.startUpdatingLocation();
        
        if (CLLocationManager.locationServicesEnabled()) {
            map.centerCoordinate=lm.location!.coordinate;
            map.showsUserLocation=true;
            let size=MKCoordinateRegionMakeWithDistance(lm.location!.coordinate, 100, 100);
            map.setRegion(size, animated: true);
        }
        
    }

}