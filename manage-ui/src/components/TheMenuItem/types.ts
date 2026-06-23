export interface Menu {
  id: string;
  name: string;
  index: string;
  icon?: string;
  children?: Menu[];
}
