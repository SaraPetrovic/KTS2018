import { Component, OnInit } from '@angular/core';
import { LineService } from 'src/app/_services/line.service';
import { Line } from 'src/app/model/line';

@Component({
  selector: 'app-administration-line-table',
  templateUrl: './administration-line-table.component.html',
  styleUrls: ['./administration-line-table.component.css']
})
export class AdministrationLineTableComponent implements OnInit {

  private lines: Line[];
  
  constructor(private lineService: LineService) { }

  ngOnInit() {
    this.lineService.getLines()
      .subscribe( data => 
        { 
          this.lines = data;
          console.log(data); 
        });
  }

}
