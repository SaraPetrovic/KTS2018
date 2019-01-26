import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-host',
  templateUrl: './host.component.html',
  styleUrls: ['./host.component.css']
})
export class HostComponent implements OnDestroy {

  currentDialog: MatDialogRef<LoginComponent> = null;
  destroy = new Subject<any>();

  constructor(matDialog: MatDialog, route: ActivatedRoute, router: Router) { 

    route.params.pipe(takeUntil(this.destroy))
      .subscribe(param => {

        if(this.currentDialog){
          this.currentDialog.close();
        }
        
        if(route.snapshot.routeConfig.path === 'login'){
          this.currentDialog = matDialog.open(LoginComponent, {height: 'auto', width: '500px'});
        }else if(route.snapshot.routeConfig.path === 'registration'){
          //this.currentDialog = matDialog.open(RegistrationComponent);
        }

        this.currentDialog.afterClosed().subscribe(result => {
          router.navigateByUrl('/');
        })
      })
  }

  ngOnDestroy(){
    this.destroy.next();
  }
}
