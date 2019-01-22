import { Component, OnInit, ViewChild } from '@angular/core';
import { QrScannerComponent } from 'angular2-qrscanner';
import { Router } from '@angular/router';
import { ConductorCommunicationService } from 'src/app/_services/conductor/communication/conductor-communication.service';

@Component({
  selector: 'app-conductor-scan',
  templateUrl: './conductor-scan.component.html',
  styleUrls: ['./conductor-scan.component.css']
})
export class ConductorScanComponent implements OnInit {

  private error = false;
  private errorMessage: string;

  @ViewChild(QrScannerComponent) qrScannerComponent: QrScannerComponent;

  constructor(private conductorCommunicationService: ConductorCommunicationService) { }

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

    this.conductorCommunicationService.scanErrorEvent.subscribe(
      (error: string) => {
        this.error = true;
        this.errorMessage = error;
        this.qrScannerComponent.startScanning.call;
      }
    );

    this.qrScannerComponent.capturedQr.subscribe((result: any) => {
     
      this.conductorCommunicationService.qrCodeScanned(result);
    });
  }

}
