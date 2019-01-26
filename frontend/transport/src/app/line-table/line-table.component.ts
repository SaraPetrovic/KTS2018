import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Line } from '../model/line';

@Component({
  selector: 'app-line-table',
  templateUrl: './line-table.component.html',
  styleUrls: ['./line-table.component.css']
})
export class LineTableComponent implements OnInit {

  @Input() lines: Line[];
  @Output() lineClickedEvent: EventEmitter<Line>;

  constructor() { 
    this.lineClickedEvent = new EventEmitter();
  }

  ngOnInit() {
  }

  lineClicked(line: Line){
    this.lineClickedEvent.emit(line);
  }
}
