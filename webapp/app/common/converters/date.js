export class DateValueConverter {
  
  monthNames = [
    "Jan", "Feb", "Mar",
    "Apr", "May", "Jun", "Jul",
    "Aug", "Sept", "Oct",
    "Nov", "Dec"
  ];
  
  toView(value) {
    
    const date = new Date(value);
    const month = this.monthNames[date.getMonth()];
    const ampm = date.getHours() >= 12 ? 'pm' : 'am';
    const hours = date.getHours() % 12 || date.getHours();
    const minutes = (`0${date.getMinutes()}`).slice(-2);
    const seconds = (`0${date.getSeconds()}`).slice(-2);
    
    return `${month} ${date.getDate()} ${date.getFullYear()} ${hours}:${minutes}:${seconds} ${ampm}`;
  }
}