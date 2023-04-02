/*
 * Copyright Xiaxi Shen @2023
 */

import React, {Component} from 'react';
import { countries } from './countries';
import { buildings } from './buildings';


interface StartProps {
    onChange(start: string): void;
}

interface StartState {
    value: string
}

class StartForm extends Component<StartProps, StartState> {
    constructor(props: StartProps) {
        super(props);
        this.state = {value: "ss"};
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event: any) {
        this.props.onChange(this.state.value)
        event.preventDefault();
    }

    getCountry() {
        return countries.map((country) => {
            return <option value={country.name}>{country.name}
            </option>;
        });
    }

    getBuildings() {
        return buildings.map((buildings) => {
            return <option value={buildings.Abbr}>{buildings.FullName}
            </option>;
        });
    }

    render() {
        return (
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Start Building:
                        <select value={this.state.value} onChange={(event) => this.setState({value: event.target.value})}>
                            <option value="NULL">
                                -- Select Building --
                            </option>
                            {this.getBuildings()}
                        </select>
                    </label>
                    <input type="submit" value="Submit" />
                </form>
        );
    }
}

export default StartForm;