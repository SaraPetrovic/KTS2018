import { Component, OnInit, ViewChild } from '@angular/core';
import { QrScannerComponent } from 'angular2-qrscanner';
import { ConductorService } from 'src/app/_services/conductor/conductor.service';
import { Router } from '@angular/router';
import { ConductorDataService } from '../../services/conductor-data.service';

@Component({
  selector: 'app-conductor-scan',
  templateUrl: './conductor-scan.component.html',
  styleUrls: ['./conductor-scan.component.css']
})
export class ConductorScanComponent implements OnInit {

  private error = false;
  private errorMessage: string;

  @ViewChild(QrScannerComponent) qrScannerComponent: QrScannerComponent;

  constructor(private conductorService: ConductorService,
              private dataService: ConductorDataService,
              private router: Router) { }

  ngOnInit() {
    this.qrScannerComponent.stopAfterScan = false;
    this.qrScannerComponent.getMediaDevices().then(devices =>{
      console.log(devices);
      const videoDevices: MediaDeviceInfo[] = [];
      for(const device of devices){
        if(device.kind.toString() === 'videoinput'){
          videoDevices.push(device);
        }
      }
      if(videoDevices.length > 0){
        let choosenDev;
        for(const dev of videoDevices){
          if(dev.label.includes('front')){
            choosenDev = dev;
            break;
          }
        }
        if(choosenDev){
          this.qrScannerComponent.chooseCamera.next(choosenDev);
        }else{
          this.qrScannerComponent.chooseCamera.next(videoDevices[0]);
        }
      }
    });  

    this.qrScannerComponent.capturedQr.subscribe((result: any) => {
     
      this.conductorService.checkTicket(result).subscribe(
        data => {
          this.dataService.scanTicket(data);
          this.router.navigate(['conductor/ticket']);
        },
        error => {
          this.error = true;
          if(error.status === 0){
            this.errorMessage = "Unable to connect to the server..."
          }else{
            this.errorMessage = error.error.errorMessage;
          }
        }
      )
    });
  }

}
