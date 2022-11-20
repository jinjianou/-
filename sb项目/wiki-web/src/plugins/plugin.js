const plugin1={
	install(vue,options){
		console.log("plugin1 第一个参数：",vue);
		console.log("plugin1 第二个参数：",options);
	}
}

export {plugin1}