/**
 * Created by jean.h.ma on 3/14/17.
 */
import React,{Component} from 'react'
import {
	View,
	Text
} from 'react-native'

export default class Hello extends Component {
	render() {
		return (
			<View style={{flexDirection:'column',alignItems:'center',flex:1,justifyContent:'center'}}>
				<Text>Hello alive push!</Text>
			</View>
		);
	}
}