export function getItems(dictType){
	return axios.get('dictItems/items/'+dictType);
}

export function getItemLabel(list,itemValue){
	//取list遍历查找value对应的label
	let item = list.find(item=>{
		if(item.itemValue==itemValue){
			return item;
		}
	});
	return item;
}