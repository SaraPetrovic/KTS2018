import { Component, OnInit } from '@angular/core';
import { Line } from '../model/line';
import { LineService } from '../_services/line.service';

@Component({
  selector: 'app-lines',
  templateUrl: './lines.component.html',
  styleUrls: ['./lines.component.css']
})
export class LinesComponent implements OnInit {

  private lines: Line[];

  constructor(private lineService: LineService) { }

  ngOnInit() {
    this.lineService.getLines()
      .subscribe(data => {
        this.lines = data;
      });
  }

}
