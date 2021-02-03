import React from 'react';
import { Pane, Dialog } from 'evergreen-ui'

const SimpleModal = (props) => {
    return (
        <Pane>
          <Dialog
            isShown={props.isVisible}
            title={props.modalTitle}
            onCloseComplete={props.onClose}
            onConfirm={props.onConfirm}
          >
            <div>
                {props.children}
            </div>
          </Dialog>
        </Pane>
    );
}

export default SimpleModal;