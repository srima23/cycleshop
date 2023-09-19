import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-returnpage',
  templateUrl: './returnpage.component.html',
  styleUrls: ['./returnpage.component.css']
})
export class ReturnpageComponent {

  newdata: any;

  constructor(private _http: HttpClient) { }

  getallcycle() {
    return this._http.get('http://localhost:8080/api/cycles/list-data');
  }

  ngOnInit() {
    this.getallcycle().subscribe({
      next: (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      error: (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    });
  }

  returnCycle(id: number, quantityToBorrow: number) {

    const requestBody = { id, count: quantityToBorrow };

    const url = `http://localhost:8080/api/cycles/${id}/return`;

    this._http.post(url, requestBody, { responseType: 'text' }).subscribe(response => {
      this.ngOnInit();
      console.log(`Cycle with ID ${id} rented successfully.`);
    });
  }
}
