'use strict';

import React, { Component } from 'react';
import { DeviceEventEmitter } from 'react-native';
import RSSignatureView, { Commands } from './src/specs/RSSignatureViewNativeComponent';

class SignatureCapture extends Component {
  constructor(props) {
    super(props);
    this.subscriptions = [];
  }

  componentDidMount() {
    if (this.props.onSaveEvent) {
      let sub = DeviceEventEmitter.addListener(
        'onSaveEvent',
        this.props.onSaveEvent
      );
      this.subscriptions.push(sub);
    }

    if (this.props.onDragEvent) {
      let sub = DeviceEventEmitter.addListener(
        'onDragEvent',
        this.props.onDragEvent
      );
      this.subscriptions.push(sub);
    }
  }

  componentWillUnmount() {
    this.subscriptions.forEach((sub) => sub.remove());
    this.subscriptions = [];
  }

  _onChange = (event) => {
    if (event.nativeEvent.pathName) {
      if (this.props.onSaveEvent) {
        this.props.onSaveEvent({
          pathName: event.nativeEvent.pathName,
          encoded: event.nativeEvent.encoded,
        });
      }
    }

    if (event.nativeEvent.dragged) {
      if (this.props.onDragEvent) {
        this.props.onDragEvent({
          dragged: event.nativeEvent.dragged,
        });
      }
    }
  };

  saveImage() {
    if (this._signatureViewRef) {
      Commands.saveImage(this._signatureViewRef);
    }
  }

  resetImage() {
    if (this._signatureViewRef) {
      Commands.resetImage(this._signatureViewRef);
    }
  }

  render() {
    return (
      <RSSignatureView
        {...this.props}
        ref={(ref) => {
          this._signatureViewRef = ref;
        }}
        onChange={this._onChange}
      />
    );
  }
}

module.exports = SignatureCapture;
