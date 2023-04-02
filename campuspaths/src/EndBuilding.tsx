/*
 * Copyright Xiaxi Shen @2023
 */



import React, {Component} from 'react';
import { countries } from './countries';
import { buildings } from './buildings';


interface EndProps {
    onChange(end: string): void;
}

interface EndState {
    value: string
}

class EndForm extends Component<EndProps, EndState> {
    constructor(props: EndProps) {
        super(props);
        this.state = {value: ""};
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
            <form onSubmit={(event) => this.handleSubmit(event)}>
                <label>
                      End Building:
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

export default EndForm;