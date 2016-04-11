package com.github.asufana.haskell;

import java.util.*;

public class SumTypes {
    
    //OCaml
    // type button = Bar | Line | Scatter
    // type state =
    //    OverWrite of button list
    //  | Normal of button
    
    //ボタン状態
    public static class ButtonsState {
        public final State state;
        public final Selected selected;
        
        public ButtonsState newState(final Button button) {
            if (state.equals(State.OverWirte)) {
                if (selected.contains(button)) {
                    return new ButtonsState(this.state, this.selected.remove(button));
                }
                else {
                    return new ButtonsState(this.state, this.selected.add(button));
                }
            }
            return new ButtonsState(this.state, new Selected(button));
        }
        
        public ButtonsState newState(final State state) {
            return new ButtonsState(state, this.selected);
        }
        
        ButtonsState(final State state, final Selected selected) {
            this.state = state;
            this.selected = selected;
        }
        
        //重ね描画モード
        public static enum State {
                                  OverWirte,
                                  Normarl;
        }
        
        //ボタン種別
        public static enum Button {
                                   Bar,
                                   Line,
                                   Scatter;
        }
        
        //選択されているボタン群
        public static class Selected {
            public final List<Button> buttons;
            
            public Selected(final Button button) {
                this(Arrays.asList(button));
            }
            
            public Selected(final List<Button> buttons) {
                this.buttons = Collections.unmodifiableList(buttons);
            }
            
            public boolean contains(final Button button) {
                return buttons.contains(button);
            }
            
            public Selected remove(final Button button) {
                final List<Button> newList = new ArrayList<>(buttons);
                newList.remove(button);
                return new Selected(buttons);
            }
            
            public Selected add(final Button button) {
                final List<Button> newList = new ArrayList<>(buttons);
                newList.add(button);
                return new Selected(buttons);
            }
        }
    }
    
}
