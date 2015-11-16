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
import CoreLocation
import Parse

class MapViewController : UIViewController, CLLocationManagerDelegate {

    
    @IBOutlet weak var map: MKMapView!
    var lm: CLLocationManager!
    
    override func viewDidLoad() {
        super.viewDidLoad();
        lm=CLLocationManager();
        lm.delegate=self;
        lm.requestAlwaysAuthorization();
        lm.desiredAccuracy=kCLLocationAccuracyBest;
        lm.startUpdatingLocation();
        addPins()
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        map.centerCoordinate=lm.location!.coordinate;
        map.showsUserLocation=true;
        let size=MKCoordinateRegionMakeWithDistance(lm.location!.coordinate, 100, 100);
        map.setRegion(size, animated: true);
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationItem.setHidesBackButton(true, animated: true)
    }
    
    func addPins() {
        let query = PFQuery(className: "Pik")
        query.limit = 20
        query.findObjectsInBackgroundWithBlock {
            (objects: [PFObject]?, error: NSError?) -> Void in
            if error != nil {
                let alert = UIAlertView()
                alert.title = "Error"
                alert.message = "Error in retrieving objects!"
                alert.addButtonWithTitle("Ok")
                alert.show()
            } else {
                // objects has all the Posts the current user liked.
                for object in objects! {
                    let pin = MKPointAnnotation()
                    pin.coordinate = CLLocationCoordinate2D.init(latitude: (object["latitude"] as? CLLocationDegrees)!, longitude: (object["longitude"] as? CLLocationDegrees)!)
                    self.map.addAnnotation(pin)
                }
            }
        }
    }

}